package com.unitedvision.sangihe.controller.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	KegiatanControllerTest.class, 
	ProgramControllerTest.class,
	AnggaranControllerTest.class,
	FisikControllerTest.class
	})
public class ControllerTests {

}
