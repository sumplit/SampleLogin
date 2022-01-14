package id.test.tada

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    RegisterInstrumentedTest::class,
    LoginInstrumentedTest::class,
    DetailInstrumentedTest::class,
    MainInstrumentedTest::class,
)

class AllTestSuit {}