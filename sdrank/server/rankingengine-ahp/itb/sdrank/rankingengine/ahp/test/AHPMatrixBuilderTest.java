package itb.sdrank.rankingengine.ahp.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import itb.sdrank.rankingengine.ahp.AHPConfig;
import itb.sdrank.rankingengine.ahp.AHPMatrix;
import itb.sdrank.rankingengine.ahp.AHPMatrixBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AHPConfig.class })
public class AHPMatrixBuilderTest {
	@Resource
	private AHPMatrixBuilder comparisonAHPMatrixBuilder;
	private AHPMatrix matrix;
	private List<String> attributes;
	private List<Float> values;
	private List<Float> expected;
	private List<Float> actual;

	@Before
	public void setUp() {
		attributes = new ArrayList<>();
		attributes.add("Q1");
		attributes.add("Q2");
		attributes.add("Q3");
		attributes.add("Q4");

		values = new ArrayList<>();
		values.add(new Float(7));
		values.add(new Float(3));
		values.add(new Float(1));
		values.add(new Float(1));
		values.add(new Float(1 / 7f));
		values.add(new Float(1 / 5f));
	}

	@Test
	public void testBuild() {
		matrix = comparisonAHPMatrixBuilder.build(attributes, values);

		actual = matrix.getRow(0);
		expected = new ArrayList<>();
		expected.add(1f);
		expected.add(7f);
		expected.add(3f);
		expected.add(1f);
		assertEquals(expected, actual);

		actual = matrix.getRow(1);
		expected = new ArrayList<>();
    expected.add(1f/7f);
    expected.add(1f);
    expected.add(1f);
    expected.add(1f/7f);
		assertEquals(expected, actual);

		actual = matrix.getRow(2);
		expected = new ArrayList<>();
    expected.add(1f/3f);
    expected.add(1f);
    expected.add(1f);
    expected.add(1f/5f);
		assertEquals(expected, actual);

		actual = matrix.getRow(3);
		expected = new ArrayList<>();
    expected.add(1f);
    expected.add(6.9999995f);
    expected.add(5f);
    expected.add(1f);
		assertEquals(expected, actual);
	}
}
