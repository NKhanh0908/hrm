package com.project.hrm.configuration;

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

        // Cấu hình font để hỗ trợ tiếng Việt
        config.put("net.sf.jasperreports.awt.ignore.missing.font", "true");
        config.put("net.sf.jasperreports.default.font.name", "Times New Roman");

        // Cấu hình export PDF
        config.put("net.sf.jasperreports.export.pdf.force.linebreak.policy", "true");
        config.put("net.sf.jasperreports.export.pdf.create.batch.mode.bookmarks", "true");

        return config;
    }

    /**
     * Utility class để load compiled reports
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