package com.project.hrm.common.cofiguration;

import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JasperReportsConfig {

    /**
     * Configuration cho JasperReports
     */
    @Bean
    public Map<String, Object> jasperReportsConfiguration() {
        Map<String, Object> config = new HashMap<>();

        config.put("net.sf.jasperreports.awt.ignore.missing.font", "true");
        config.put("net.sf.jasperreports.default.font.name", "Times New Roman");

        config.put("net.sf.jasperreports.export.pdf.force.linebreak.policy", "true");
        config.put("net.sf.jasperreports.export.pdf.create.batch.mode.bookmarks", "true");

        return config;
    }

    /**
     * Utility class load compiled reports
     */
    public static class ReportLoader {

        public static Object loadCompiledReport(String reportPath) throws Exception {
            ClassPathResource resource = new ClassPathResource(reportPath);
            return JRLoader.loadObject(resource.getInputStream());
        }

        public static String getReportPath(String reportName) {
            return "/reports/" + reportName;
        }
    }
}