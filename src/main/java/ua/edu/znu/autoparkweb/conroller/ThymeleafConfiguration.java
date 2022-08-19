package ua.edu.znu.autoparkweb.conroller;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@WebListener
public class ThymeleafConfiguration implements ServletContextListener {
    public static final String TEMPLATE_ENGINE_ATR = "ua.edu.znu.servletstudy.TemplateEngineInstance";
    private ITemplateEngine templateEngine;
    private JakartaServletWebApplication application;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.application = JakartaServletWebApplication.buildApplication(sce.getServletContext());
        this.templateEngine = getTemplateEngine(this.application);
        sce.getServletContext().setAttribute(TEMPLATE_ENGINE_ATR, templateEngine);
    }

    private ITemplateEngine getTemplateEngine(IWebApplication application) {
        TemplateEngine templateEngine = new TemplateEngine();
        WebApplicationTemplateResolver templateResolver = getTemplateResolver(application);
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    private WebApplicationTemplateResolver getTemplateResolver(IWebApplication application) {
        WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
        templateResolver.setCacheable(true);
        return templateResolver;
    }
}
