package com.queen.core.tool.yml;

import com.queen.core.tool.utils.StringUtil;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * yml配置加载
 *
 * @author jensen
 */
public class YmlPropertyLoaderFactory extends DefaultPropertySourceFactory {

	@Override
	public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource encodedResource) throws IOException {
		if (encodedResource == null) {
			return emptyPropertySource(name);
		}
		Resource resource = encodedResource.getResource();
		String fileName = resource.getFilename();
		List<PropertySource<?>> sources = new YamlPropertySourceLoader().load(fileName, resource);
		if (sources.isEmpty()) {
			return emptyPropertySource(fileName);
		}
		// yml 数据存储，合成一个 PropertySource
		Map<String, Object> ymlDataMap = new HashMap<>(32);
		for (PropertySource<?> source : sources) {
			ymlDataMap.putAll(((MapPropertySource) source).getSource());
		}
		return new OriginTrackedMapPropertySource(getSourceName(fileName, name), ymlDataMap);
	}

	private static PropertySource<?> emptyPropertySource(@Nullable String name) {
		return new MapPropertySource(getSourceName(name), Collections.emptyMap());
	}

	private static String getSourceName(String... names) {
		return Stream.of(names)
			.filter(StringUtil::isNotBlank)
			.findFirst()
			.orElse("QueenYmlPropertySource");
	}

}
