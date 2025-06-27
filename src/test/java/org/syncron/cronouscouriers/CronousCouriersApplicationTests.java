package org.syncron.cronouscouriers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.syncron.cronouscouriers.dispatchservice.DispatchCenter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

@SpringBootTest
class CronousCouriersApplicationTests {

	CronousCouriersApplication cronousCouriersApplication;
	@Test
	void contextLoads() {
	}


}
