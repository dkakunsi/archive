package itb.sdrank.rankingengine.ahp;

import java.util.ArrayList;
import java.util.List;

public class AHPMatrix {
	private List<List<Float>> matrix;
	private List<String> attributes;

	public AHPMatrix(List<String> attributes) {
		this.attributes = attributes;
		matrix = new ArrayList<>();
		int row, column;

		for (row = 0; row < attributes.size(); row++) {
			List<Float> List = new ArrayList<>();
			for (column = 0; column < attributes.size(); column++) {
				List.add(null);
			}

			matrix.add(List);
		}
	}

	public List<String> getAttributes() {
		return attributes;
	}

	public List<Float> sumOfRows() {
		List<Float> sumOfRows = new ArrayList<>();

		for (List<Float> row : matrix) {
			sumOfRows.add(sumOfRow(row));
		}

		return sumOfRows;
	}

	private Float sumOfRow(List<Float> row) {
		Float meanOfRow = 0f;

		for (Float f : row) {
			meanOfRow += f;
		}

		return meanOfRow;
	}

	public List<Float> sumOfColumns() {
		List<Float> sumOfColumns = new ArrayList<>();
		int row, column;
		Float sumOfColumn;

		for (column = 0; column < size(); column++) {
			sumOfColumn = 0f;
			for (row = 0; row < size(); row++) {
				sumOfColumn += getValue(row, column);
			}

			sumOfColumns.add(sumOfColumn);
		}

		return sumOfColumns;
	}

	public List<Float> meanOfRows() {
		List<Float> meanOfRows = new ArrayList<>();
		for (List<Float> row : matrix) {
			meanOfRows.add(sumOfRow(row) / row.size());
		}

		return meanOfRows;
	}

	public Float getValue(Integer row, Integer column) {
		return matrix.get(row).get(column);
	}

	public void setValue(Integer row, Integer column, Float value) {
		matrix.get(row).set(column, value);
	}

	public List<Float> getRow(Integer rowNumber) {
		return matrix.get(rowNumber);
	}

	public Integer size() {
		return attributes.size();
	}

	@Override
	public String toString() {
		String str = "";
		int i;

		for (i = 0; i < matrix.size(); i++) {
			List<Float> v = matrix.get(i);
			str = String.format("%s\n%s", str, v.toString());
		}

		return str;
	}
}
