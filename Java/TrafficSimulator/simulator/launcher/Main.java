package simulator.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.Factory;
import simulator.factories.MostCrowdedStrategyBuilder;
import simulator.factories.MoveAllStrategyBuilder;
import simulator.factories.MoveFirstStrategyBuilder;
import simulator.factories.NewCityRoadEventBuilder;
import simulator.factories.NewInterCityRoadEventBuilder;
import simulator.factories.NewJunctionEventBuilder;
import simulator.factories.NewVehicleEventBuilder;
import simulator.factories.RoundRobinStrategyBuilder;
import simulator.factories.SetContClassEventBuilder;
import simulator.factories.SetWeatherEventBuilder;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.TrafficSimulator;
import simulator.view.MainWindow;

public class Main {

	private final static Integer _timeLimitDefaultValue = 10;
	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;
	private static int _t;
	private static String mode;

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseModeOption(line);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseticksOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Choose the mode").build());
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").hasArg().desc("Number of ticks").build());
		return cmdLineOptions;
	}

	private static void parseModeOption(CommandLine line) throws ParseException {
		String m = line.getOptionValue("m");
		
		if(m == null) mode = "gui";
		else if(m.equalsIgnoreCase("gui") || m.equalsIgnoreCase("console"))
		{
			mode = m;
		}
		
		else
			throw new ParseException("Incorrect mode");
		
		
		
	}
	
	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) {
		_inFile = line.getOptionValue("i");

	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}
	
	private static void parseticksOption(CommandLine line) throws ParseException {
		String t = line.getOptionValue("t");
		if(t != null)
		{
			_t = Integer.parseInt(t);
		}
		else _t = _timeLimitDefaultValue;
		
	}

	private static void initFactories() {

		// TODO complete this method to initialize _eventsFactory
		ArrayList<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add( new RoundRobinStrategyBuilder("round_robin_lss"));
		lsbs.add( new MostCrowdedStrategyBuilder("most_crowded_lss") ); 
		Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);
		
		ArrayList<Builder<DequeuingStrategy>> dqbs = new ArrayList<>(); 
		dqbs.add( new MoveFirstStrategyBuilder("move_first_dqs"));
		dqbs.add( new MoveAllStrategyBuilder("move_all_dqs") );
		Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>( dqbs);
		
		ArrayList<Builder<Event>> ebs = new ArrayList<>(); 
		ebs.add( new NewJunctionEventBuilder("new_junction",lssFactory,dqsFactory) ); 
		ebs.add( new NewCityRoadEventBuilder("new_city_road") ); 
		ebs.add( new NewInterCityRoadEventBuilder("new_inter_city_road") );
		ebs.add(new NewVehicleEventBuilder("new_vehicle"));
		ebs.add(new SetContClassEventBuilder("set_cont_class"));
		ebs.add(new SetWeatherEventBuilder("set_weather"));
		_eventsFactory = new BuilderBasedFactory<>(ebs);

	}

	private static void startBatchMode() throws IOException, ParseException {
		// TODO complete this method to start the simulation
		if (_inFile == null) {
			throw new ParseException("An events file is missing");
		}
		InputStream is = new FileInputStream(new File(_inFile));
		OutputStream os = null;
		if (_outFile == null)
		      os = System.out;
		else
		      os = new FileOutputStream(new File(_outFile));
		
		TrafficSimulator tf = new TrafficSimulator();
		Controller c = new Controller(tf, _eventsFactory);

		c.loadEvents(is);
		c.run(_t, os);
	}

	private static void startGuiMode() throws IOException {
		
		TrafficSimulator tf = new TrafficSimulator();
		Controller c= new Controller(tf,_eventsFactory);
		if(_inFile != null) 
			{
				System.out.println("hola");
				InputStream is = new FileInputStream(new File(_inFile));
				c.loadEvents(is);
			}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow(c);
			}
		});


	}
	private static void start(String[] args) throws IOException, ParseException {
		initFactories();
		parseArgs(args);
		if(mode.equalsIgnoreCase("gui")) startGuiMode();
		else startBatchMode();
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help
	
	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
