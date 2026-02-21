package itb.sdrank.rankingengine.ahp;

import java.util.ArrayList;
import java.util.List;

public class AHP {
	private AHPMatrix matrix;

	public AHP(AHPMatrix matrix) {
	  super();
		this.matrix = matrix;
	}

	public AHPMatrix getMatrix() {
		return matrix;
	}
	
	public Integer getIndex(String attribute) {
	  return matrix.getAttributes().indexOf(attribute);
	}

	public AHPMatrix getNormalizedMatrix() {
		AHPMatrix normalizedMatrix = new AHPMatrix(matrix.getAttributes());
		int rowNumber, columnNumber;
		Float normalizedValue;
		List<Float> sumOfColumns = matrix.sumOfColumns();

		for (rowNumber = 0; rowNumber < matrix.size(); rowNumber++) {
			for (columnNumber = 0; columnNumber < matrix.size(); columnNumber++) {
				normalizedValue = matrix.getValue(rowNumber, columnNumber) / sumOfColumns.get(columnNumber);
				normalizedMatrix.setValue(rowNumber, columnNumber, normalizedValue);
			}
		}

		return normalizedMatrix;
	}

	public List<Float> getPriorities() {
		return getNormalizedMatrix().meanOfRows();
	}

	public List<Float> getConsistencyMeasure() {
		List<Float> consistencyMeasure = new ArrayList<>();
		List<Float> priorities = getPriorities();
		List<Float> row;
		Float measuredValue;
		int rowNumber, columnNumber, size = matrix.size();

		for (rowNumber = 0; rowNumber < size; rowNumber++) {
			measuredValue = 0f;
			row = matrix.getRow(rowNumber);

			for (columnNumber = 0; columnNumber < size; columnNumber++) {
				measuredValue += row.get(columnNumber) * priorities.get(columnNumber);
			}

			consistencyMeasure.add(measuredValue / priorities.get(rowNumber));
		}

		return consistencyMeasure;
	}

	public Float getConsistencyIndex() {
		Float consistencyIndex = 0f;
		List<Float> consistencyMeasure = getConsistencyMeasure();
		int index;

		for (index = 0; index < consistencyMeasure.size(); index++) {
			consistencyIndex += consistencyMeasure.get(index);
		}

		consistencyIndex /= consistencyMeasure.size(); // hitung rata-rata

		return (consistencyIndex - consistencyMeasure.size()) / (consistencyMeasure.size() - 1);
	}

	public Float getConsistencyRatio() {
		Float consistencyIndex = getConsistencyIndex();
		Float randomIndex = getRandomIndex();

		return consistencyIndex / randomIndex;
	}
	
	public Boolean isFine() {
	  return getConsistencyRatio() < 0.1;
	}

	protected Float getRandomIndex() {
		Integer matrixSize = matrix.size();
		Float randomIndex = 0f;

		switch (matrixSize) {
		case 3:
			randomIndex = 0.58f;
			break;
		case 4:
			randomIndex = 0.9f;
			break;
		case 5:
			randomIndex = 1.12f;
			break;
		case 6:
			randomIndex = 1.24f;
			break;
		case 7:
			randomIndex = 1.32f;
			break;
		case 8:
			randomIndex = 1.41f;
			break;
		case 9:
			randomIndex = 1.46f;
			break;
		case 10:
			randomIndex = 1.49f;
			break;
		}

		return randomIndex;
	}
}
