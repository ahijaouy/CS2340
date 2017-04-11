package com.goat.thirsty_goat;

import com.goat.thirsty_goat.models.HistoricalReportManager;
import com.goat.thirsty_goat.models.PurityCondition;
import com.goat.thirsty_goat.models.PurityReport;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Walker on 4/5/17.
 */

public class HistoricalReportManagerTest {
    HistoricalReportManager reportManager = HistoricalReportManager.getInstance();
    List<PurityReport> testList;

    @Before
    public void setUp() throws Exception {
        testList = new LinkedList<>();
    }

    @Test
    public void testFilteredReportsSize1() {
        testList.add(new PurityReport(PurityCondition.SAFE, 150, 228, "Tester", 1, 30, 2015));
        List<PurityReport> result = reportManager.filterPurityReports(testList, 2015);
        Assert.assertEquals("Size wrong after adding 1 purity report", 1, result.size());
    }

    @Test
    public void testFilteredReportsSize2() {
        testList.add(new PurityReport(PurityCondition.SAFE, 150, 228, "Tester0", 1, 30, 2014));
        testList.add(new PurityReport(PurityCondition.TREATABLE, 32, 0, "Tester1", 2, 9, 2012));
        testList.add(new PurityReport(PurityCondition.UNSAFE, 0, 0, "Tester2", 1, 30, 2014));
        testList.add(new PurityReport(PurityCondition.SAFE, 600, 60, "Tester3", 11, 22, 2014));
        List<PurityReport> result = reportManager.filterPurityReports(testList, 2014);
        Assert.assertEquals("Size wrong after adding 4 purity reports", 3, result.size());
    }

    @Test
    public void testFilteredReportsSize3() {
        testList.add(new PurityReport(PurityCondition.SAFE, 150, 228, "Tester0", 1, 30, 2014));
        testList.add(new PurityReport(PurityCondition.TREATABLE, 32, 0, "Tester1", 2, 9, 2012));
        testList.add(new PurityReport(PurityCondition.UNSAFE, 0, 0, "Tester2", 1, 30, 2014));
        testList.add(new PurityReport(PurityCondition.SAFE, 600, 60, "Tester3", 11, 22, 2014));
        try {
            List<PurityReport> result = reportManager.filterPurityReports(testList, 2018);
            Assert.fail("Did not throw an exception when the filtered list was empty.");
        } catch (NoSuchElementException exc) {
            // if you get here, the test was successful
        }
    }
}
