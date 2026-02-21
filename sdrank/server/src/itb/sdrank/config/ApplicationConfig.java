package itb.sdrank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import itb.sdrank.cache.redis.RedisConfig;
import itb.sdrank.discoveryengine.lucene.LuceneConfig;
import itb.sdrank.notificationengine.activemq.ActiveMQConfig;
import itb.sdrank.rankingengine.ahp.AHPConfig;
import itb.sdrank.storage.filesystem.FSConfig;

/**
 * System configuration.
 * 
 * @author Deddy Christoper Kakunsi Created: Feb 13, 2018
 *
 */
@Configuration
@Import({AHPConfig.class, LuceneConfig.class, FSConfig.class, RedisConfig.class, ActiveMQConfig.class})
@ComponentScan("itb.sdrank")
public class ApplicationConfig {

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}
}
