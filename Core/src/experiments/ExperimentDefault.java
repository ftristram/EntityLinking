package experiments;

import iniloader.IniLoader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

import stopwatch.Stopwatch;
import configuration.Config;
import datasetEvaluator.DatasetEvaluatorSandbox;

public class ExperimentDefault {

	static int experimentNumber = 9;
	static String dataset = "aida";

	public static void main(String[] args) {
		String filepath = "../config.ini";
		if (args.length == 1)
			filepath = args[0];
		System.out.println("Using config file: " + filepath);
		IniLoader iniLoader = new IniLoader();
		iniLoader.parse(filepath);
		double result = 0;

		double resultFold0 = 0;
		double resultFold1 = 0;
		Config config = Config.getInstance();
		dataset = config.getParameter("datasetPath");
		
		try {
			BufferedWriter fw = new BufferedWriter(new FileWriter("experiment_" + experimentNumber + ".txt"));
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String formattedDate = sdf.format(date);
			fw.write(formattedDate + "\n");
			
			Stopwatch swTotal = new Stopwatch(Stopwatch.UNIT.MINUTES);
			for (int trainFold = 0; trainFold <= 1; trainFold++) {
				int testFold = (trainFold + 1) % 2;

				// set experiment file path
				config.setParameter("datasetPath", "/home/felix/data/" + dataset + "_2fold_" + trainFold + ".tsv");

				fw.write("Experiment number " + experimentNumber + "(" + config.getParameter("datasetPath") + ")\n\n");
				fw.write("id\tcorrect\t\tPR\tLamda\tTime\n");

				double bestScore = 0;
				double bestPR = 0;
				double bestL = 0;
				int id = 0;
				int bestID = 0;
				Stopwatch sw = new Stopwatch(Stopwatch.UNIT.SECONDS);

				for (int pageRankFactor = 0; pageRankFactor <= 20; pageRankFactor++) {
					double pageRank = round(0.05 * pageRankFactor, 2);
					config.setParameter("vector_evaluation_pageRankWeight", Double.toString(pageRank));

					for (int lamdaFactor = 0; lamdaFactor <= 20; lamdaFactor++) {
						double lambda = round(0.05 * lamdaFactor, 2);
						config.setParameter("vector_evaluation_lamda", Double.toString(lambda));

						sw.start();
						result = DatasetEvaluatorSandbox.evaluate();
						sw.stop();
						result = round(result, 4);
						fw.write(id + ":\t" + result + "%\t" + pageRank + "\t" + lambda + "\t" + round(sw.doubleTime, 4) + " s\n");
						fw.flush();

						if (result > bestScore) {
							bestScore = result;
							bestPR = pageRank;
							bestL = lambda;
							bestID = id;
						}
						id++;
					}
				}

				fw.write("Best result: " + Double.toString(bestScore) + "\n");
				fw.write("id: " + bestID + " PR: " + bestPR + " L: " + bestL + "\n");

				config.setParameter("vector_evaluation_pageRankWeight", Double.toString(bestPR));
				config.setParameter("vector_evaluation_lamda", Double.toString(bestL));
				config.setParameter("datasetPath", "/home/felix/data/" + dataset + "_2fold_" + testFold + ".tsv");
				result = DatasetEvaluatorSandbox.evaluate();
				fw.write("Result on testset: " + result + "\n");
				fw.write("(" + config.getParameter("datasetPath") + ")\n");
				if (testFold == 0) {
					resultFold0 = result;
				} else if (testFold == 1) {
					resultFold1 = result;
				}
				fw.write("\n");
			}
			swTotal.stop();
			fw.write("Total time: " + round(swTotal.doubleTime, 4) + " minutes.\n");
			double resultAverage = (resultFold0 + resultFold1) / 2.0;
			fw.write("Average of the two results: " + resultAverage);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}