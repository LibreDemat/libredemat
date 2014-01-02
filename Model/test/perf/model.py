from net.grinder.script.Grinder import grinder
from net.grinder.script import Test
from junit.framework import TestSuite
from junit.framework import TestResult

from org.libredemat.service.authority import LocalReferentialServiceTest
from org.libredemat.service.authority import CategoryServiceTest
from org.libredemat.service.users import DocumentServiceTest
from org.libredemat.service.users import RequestServiceTest
from org.libredemat.service.users import HomeFolderModificationRequestServiceTest
from org.libredemat.service.ecitizen import VoCardRequestServiceTest
from org.libredemat.external import FakeExternalServiceTest

class TestRunner:
    def __call__(self):
    # Turn off automatic reporting for the current worker thread.
    # Having done this, the script can modify or set the statistics
    # before they are sent to the log and the console.
        grinder.statistics.delayReports = 1

    # Creates a Test Suite.
    	suite = TestSuite(VoCardRequestServiceTest().getClass());
    	suite.addTestSuite(RequestServiceTest().getClass());
    	suite.addTestSuite(DocumentServiceTest().getClass());
    	suite.addTestSuite(FakeExternalServiceTest().getClass());
    	suite.addTestSuite(LocalReferentialServiceTest().getClass());
    	suite.addTestSuite(CategoryServiceTest().getClass());
    	#suite.addTestSuite(HomeFolderModificationRequestServiceTest().getClass());
        
    # Returns the tests as an enumeration.
        tests = suite.tests();

    # Iterate over the tests.
        testNumber = 0
        for test in tests:
            testNumber += 1
            testCase = Test(testNumber, test.getName()).wrap(suite)

            testResult = TestResult()
            testCase.runTest(test, testResult)

        if testResult.errorCount() > 0:
            grinder.statistics.success = 0
        elif testResult.failureCount() > 0:
            grinder.statistics.success = 0
