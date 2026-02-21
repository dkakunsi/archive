package itb.sdrank.rankingengine.ahp;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AHPMatrixBuilder {
	private List<Float> values;
	private AHPMatrix matrix;
	private Integer matrixSize;
	private final Float DIAGONAL_VALUE = new Float(1);

	public AHPMatrix build(List<String> attributes, List<Float> values) {
		matrix = new AHPMatrix(attributes);
		this.values = values;
		this.matrixSize = attributes.size();

		setDiagonalValue();
		setComparisonValue();
		setPairedValue();

		return matrix;
	}

	private void setDiagonalValue() {
		int index;
		for (index = 0; index < matrixSize; index++) {
			matrix.setValue(index, index, DIAGONAL_VALUE);
		}
	}

	private void setComparisonValue() {
		int index = 0, row, column;
		for (row = 0; row < matrixSize; row++) {
			for (column = row + 1; column < matrixSize; column++, index++) {
				float value = values.get(index);
				matrix.setValue(row, column, value);
			}
		}
	}

	private void setPairedValue() {
		int row, column;
		for (row = 1; row < matrixSize; row++) {
			for (column = 0; column < row; column++) {
				matrix.setValue(row, column, (1 / matrix.getValue(column, row)));
			}
		}
	}
}
