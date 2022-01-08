package com.haeger;

import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com.haeger")
public class RunCucumberTest {

}
