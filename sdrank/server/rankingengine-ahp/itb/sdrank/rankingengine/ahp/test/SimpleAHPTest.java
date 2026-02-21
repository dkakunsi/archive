package itb.sdrank.rankingengine.ahp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import itb.sdrank.rankingengine.ahp.AHP;
import itb.sdrank.rankingengine.ahp.AHPConfig;
import itb.sdrank.rankingengine.ahp.AHPMatrix;
import itb.sdrank.rankingengine.ahp.AHPMatrixBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AHPConfig.class })
public class SimpleAHPTest {
	@Resource
	private AHPMatrixBuilder comparisonAHPMatrixBuilder;
	private AHPMatrix matrix;
	private AHP ahp;
	private List<String> attributes;
	private List<Float> values;
	private List<Float> expected, actual;

	@Before
	public void setUp() throws Exception {
		attributes = new ArrayList<>();
		attributes.add("Q1");
		attributes.add("Q2");
		attributes.add("Q3");
		attributes.add("Q4");
		attributes.add("Q5");

		values = new ArrayList<>();
		values.add(new Float(7));
		values.add(new Float(3));
		values.add(new Float(1));
		values.add(new Float(1));

		values.add(new Float(1 / 7f));
		values.add(new Float(1 / 5f));
		values.add(new Float(1 / 5f));

		values.add(new Float(1));
		values.add(new Float(1));

		values.add(new Float(1));

		matrix = comparisonAHPMatrixBuilder.build(attributes, values);
		ahp = new AHP(matrix);
	}

	@Test
	public void testGetNormalizedMatrix() {
		AHPMatrix normalizedMatrix = ahp.getNormalizedMatrix();

		actual = normalizedMatrix.getRow(0);
		expected = new ArrayList<>();
		expected.add(0.28767124f);
		expected.add(0.28f);
		expected.add(0.4883721f);
		expected.add(0.23809525f);
		expected.add(0.23809525f);
		assertEquals(expected, actual);

		actual = normalizedMatrix.getRow(1);
		expected = new ArrayList<>();
		expected.add(0.04109589f);
		expected.add(0.04f);
		expected.add(0.023255816f);
		expected.add(0.047619052f);
		expected.add(0.047619052f);
		assertEquals(expected, actual);

		actual = normalizedMatrix.getRow(2);
		expected = new ArrayList<>();
		expected.add(0.09589041f);
		expected.add(0.27999997f);
		expected.add(0.1627907f);
		expected.add(0.23809525f);
		expected.add(0.23809525f);
		assertEquals(expected, actual);

		actual = normalizedMatrix.getRow(3);
		expected = new ArrayList<>();
		expected.add(0.28767124f);
		expected.add(0.2f);
		expected.add(0.1627907f);
		expected.add(0.23809525f);
		expected.add(0.23809525f);
		assertEquals(expected, actual);

		actual = normalizedMatrix.getRow(4);
		expected = new ArrayList<>();
		expected.add(0.28767124f);
		expected.add(0.2f);
		expected.add(0.1627907f);
		expected.add(0.23809525f);
		expected.add(0.23809525f);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetPriorities() {
		actual = ahp.getPriorities();
		expected = new ArrayList<>();

		expected.add(0.3064468f);
		expected.add(0.039917964f);
		expected.add(0.20297432f);
		expected.add(0.2253305f);
		expected.add(0.2253305f);

		assertEquals(expected, actual);
	}

	@Test
	public void testGetConsistencyMeasure() {
		actual = ahp.getConsistencyMeasure();
		expected = new ArrayList<>();

		expected.add(5.3694687f);
		expected.add(5.081036f);
		expected.add(5.100202f);
		expected.add(5.146538f);
		expected.add(5.146538f);

		assertEquals(expected, actual);
	}

	@Test
	public void testGetConsistencyIndex() {
		Float actual = ahp.getConsistencyIndex();
		Float expected = 0.04218912f;
		assertEquals(expected, actual);
	}

	@Test
	public void testGetConsistencyRatio() {
		Float actual = ahp.getConsistencyRatio();
		Float expected = 0.037668858f;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFine() {
	  assertTrue(ahp.isFine());
	}

}
