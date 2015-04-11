package like2ch4j.main;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Run {

	private String boardName;

	public static void main(String[] args) {
		Post.testpost();
		//new Run().start(args);

	}

	private void start(String[] args) {
		Options opts = new Options();
		opts.addOption("b", "board", true, "掲示板を開いて起動します。");
		BasicParser optParser = new BasicParser();
		CommandLine cl;
		HelpFormatter help = new HelpFormatter();

		try {

			cl = optParser.parse(opts, args);
			boardName = cl.getOptionValue("b");
			System.out.println("like2ch4j started...");

			if(boardName != null){
				System.out.println("with Board:" + boardName);
			}

		} catch (ParseException e) {
			help.printHelp("like2ch4j", opts);
		}
	}
}
