package cn.njcit.common;

import cn.njcit.common.constants.AppConstants;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 *       
 * 项目名称：ubgw-core
 * 类名称：AppConfigurer
 * 类描述：  加载配置信息
 *
 * 创建人:yangkai2@ofcard.com
 * 创建时间：2014-04-14
 *
 */
public class AppConfigurer extends PropertyPlaceholderConfigurer {
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
        AppConstants.loadAppConfig(props);
    }
}