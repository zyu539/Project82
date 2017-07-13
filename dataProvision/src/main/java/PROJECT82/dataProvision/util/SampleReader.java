package PROJECT82.dataProvision.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import PROJECT82.dataProvision.domain.RawPosition;

public class SampleReader {

	public static List<RawPosition> readRoute(String textFile) {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		List<RawPosition> list = new ArrayList<RawPosition>();
		try {
			br = new BufferedReader(new FileReader(textFile));
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] point = line.split(cvsSplitBy);
				RawPosition rp = new RawPosition(Integer.parseInt(point[0]), Double.parseDouble(point[2]), Double.parseDouble(point[1]), Integer.parseInt(point[3]), point[4]);
//				if ("1".equals(point[3]) && "0".equals(previous)) {
//					r = new Route(60);
//				} else if ("1".equals(point[3])) {
//					List<Position> tmp = r.getPositions();
//					tmp.add(new Position(Double.parseDouble(point[2]), Double.parseDouble(point[1]), point[4]));
//					r.setPositions(tmp);
//				} else if ("0".equals(point[3]) && "1".equals(previous)) {
//					list.add(r);
//				}
//				previous = point[3];
				list.add(rp);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

}