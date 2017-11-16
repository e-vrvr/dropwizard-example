/*
 * Copyright (C) 2015 Marc Rufer (m.rufer@gmx.ch)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.rufer.playground.dropwizard;

import be.rufer.playground.dropwizard.config.ApplicationConfiguration;
import be.rufer.playground.dropwizard.health.TemplateHealthCheck;
import be.rufer.playground.dropwizard.resource.ExampleResource;
import com.codahale.metrics.CachedGauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.izettle.metrics.dw.InfluxDbReporterFactory;
import com.izettle.metrics.dw.SenderType;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.commons.lang3.RandomUtils;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.codahale.metrics.MetricRegistry.name;
import static java.lang.Long.valueOf;
import static java.util.concurrent.TimeUnit.MINUTES;

public class ExampleApplication extends Application<ApplicationConfiguration> {

    public static void main(String[] args) throws Exception {
        new ExampleApplication().run(args);
    }

    @Override
    public String getName() {
        return "example";
    }

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
    }

    @Override
    public void run(ApplicationConfiguration configuration,
                    Environment environment) {
        final ExampleResource resource = new ExampleResource(
                configuration.getMessageTemplate(),
                configuration.getDefaultName()
        );
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getMessageTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);

        MetricRegistry metricRegistry = environment.metrics();

        metricRegistry.register("gc", new GarbageCollectorMetricSet());
        metricRegistry.register("memory", new MemoryUsageGaugeSet());
        metricRegistry.register("threads", new ThreadStatesGaugeSet());

        Servlet metricsServlet = new MetricsServlet(metricRegistry);
        ServletHolder holder = new ServletHolder(metricsServlet);
        environment.getApplicationContext().getServletHandler().addServletWithMapping(holder, "/metrics");

        metricRegistry.register(name(ExampleApplication.class, "total-offers-count"), new CachedGauge(10, MINUTES) {

            protected Long loadValue() {
                return RandomUtils.nextLong(50, 100);
            }
        });
        metricRegistry.register(name(ExampleApplication.class, "active-offers-count"), new CachedGauge(10, MINUTES) {

            protected Long loadValue() {
                return RandomUtils.nextLong(200, 1000);
            }
        });
        metricRegistry.register(name(ExampleApplication.class, "total-statuses-count"), new CachedGauge(10, MINUTES) {

            protected Long loadValue() {
                return RandomUtils.nextLong(10, 40);
            }
        });

        InfluxDbReporterFactory influxDbReporterFactory = new InfluxDbReporterFactory();
        influxDbReporterFactory.setDatabase("metrics");
        influxDbReporterFactory.setHost("173.44.45.49");
        influxDbReporterFactory.setPort(8086);
        influxDbReporterFactory.setSenderType(SenderType.HTTP);


        ScheduledReporter reporter = influxDbReporterFactory.build(metricRegistry);
        reporter.start(10, TimeUnit.SECONDS);
    }
}
