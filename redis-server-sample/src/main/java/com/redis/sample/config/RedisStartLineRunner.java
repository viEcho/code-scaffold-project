package com.redis.sample.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * redis起跑线转轮
 *
 * @author echo
 * @date 2024/01/26
 */
@Component
public class RedisStartLineRunner implements CommandLineRunner {
    /**
     * 运行
     *
     * @param args args
     * @throws Exception 例外
     */
    @Override
    public void run(String... args) throws Exception {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource("shell/redis-start.sh");
            String scriptPath = new File(resource.getFile()).getAbsolutePath();
            // 执行脚本
            Process process = Runtime.getRuntime().exec(scriptPath);

            // 获取脚本执行的输出信息
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            // 等待脚本执行完成
            int exitCode = process.waitFor();
            System.out.println("脚本执行完成，退出码：" + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

