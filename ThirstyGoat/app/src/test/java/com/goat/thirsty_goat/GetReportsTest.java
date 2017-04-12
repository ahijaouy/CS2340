package com.goat.thirsty_goat;

import com.goat.thirsty_goat.models.Location;
import com.goat.thirsty_goat.models.ReportManager;
import com.goat.thirsty_goat.models.SourceCondition;
import com.goat.thirsty_goat.models.SourceReport;
import com.goat.thirsty_goat.models.SourceType;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;


/**
 * Created by GabrielNAN on 4/11/17.
 */

public class GetReportsTest {
    ReportManager reportManager;

    @Before
    public void setUp() throws Exception {
        reportManager = ReportManager.getInstance();
        reportManager.clearReports();
    }

    @Test
    public void testGetReport1() {
        Location loc1 = new Location(1, 1);
        LocalDateTime dateTime1 = LocalDateTime.now();
        SourceReport sourceReport1 = new SourceReport(SourceType.BOTTLED, SourceCondition.POTABLE, "Test1", 1, dateTime1);
        reportManager.setOldSourceReport(loc1, sourceReport1);

        Location loc2 = new Location(2, 2);
        LocalDateTime dateTime2 = LocalDateTime.now();
        SourceReport sourceReport2 = new SourceReport(SourceType.LAKE, SourceCondition.TREATABLE_CLEAR, "Test2", 2, dateTime2);
        reportManager.setOldSourceReport(loc2, sourceReport2);

        Location loc3 = new Location(3, 3);
        LocalDateTime dateTime3 = LocalDateTime.now();
        SourceReport sourceReport3 = new SourceReport(SourceType.SPRING, SourceCondition.TREATABLE_MUDDY, "Test3", 3, dateTime3);
        reportManager.setOldSourceReport(loc3, sourceReport3);

        assertSame(sourceReport1, reportManager.getReport(1).getSourceReport());
        assertSame(sourceReport2, reportManager.getReport(2).getSourceReport());
        assertSame(sourceReport3, reportManager.getReport(3).getSourceReport());
    }

    @Test
    public void testGetReport2() {
        Location loc1 = new Location(1, 1);
        LocalDateTime dateTime1 = LocalDateTime.now();
        SourceReport sourceReport1 = new SourceReport(SourceType.BOTTLED, SourceCondition.POTABLE, "Test1", 1, dateTime1);
        reportManager.setOldSourceReport(loc1, sourceReport1);

        Location loc2 = new Location(2, 2);
        LocalDateTime dateTime2 = LocalDateTime.now();
        SourceReport sourceReport2 = new SourceReport(SourceType.LAKE, SourceCondition.TREATABLE_CLEAR, "Test2", 2, dateTime2);
        reportManager.setOldSourceReport(loc2, sourceReport2);

        Location loc3 = new Location(3, 3);
        LocalDateTime dateTime3 = LocalDateTime.now();
        SourceReport sourceReport3 = new SourceReport(SourceType.SPRING, SourceCondition.TREATABLE_MUDDY, "Test3", 3, dateTime3);
        reportManager.setOldSourceReport(loc3, sourceReport3);

        assertNull(reportManager.getReport(0));
    }

    @Test
    public void testGetReport3() {
        assertNull(reportManager.getReport(0));
    }
}
