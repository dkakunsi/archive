package itb.sdrank.discoveryengine.lucene;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import itb.sdrank.storage.filesystem.FSConfig;

@Configuration
@ComponentScan("itb.sdrank.discoveryengine.lucene")
@Import(FSConfig.class)
public class LuceneConfig {

}
