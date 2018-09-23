package config;

import cashes.CacheTop;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.io.FileSystemResource;
import util.DTOObject;
import util.SomeObject;

@Configuration
@ComponentScan
public class CacheCFG {

    @Bean
    @Description("Кэш верхнего уровня")
    CacheTop CacheTop(){
        return new CacheTop();
    }

}
