package com.cnten;

import com.cnten.vertx.SpringVerticleFactory;
import com.cnten.vertx.SpringVerticleManager;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.cnten.platform.**.dao")
public class DeviceGatherApplication {

	@Autowired
	private SpringVerticleFactory verticleFactory;
	@Autowired
	private SpringVerticleManager manager;

	/**
	 * The Vert.x worker pool size, configured in the
	 * {@code application.properties} file.
	 * <p>
	 * Make sure this is greater than {@link #springWorkerInstances}.
	 */
	@Value("${vertx.worker.pool.size}")
	int workerPoolSize;

	/**
	 * The Vert.x worker pool size, configured in the
	 * {@code application.properties} file.
	 * <p>
	 * Make sure this is greater than {@link #springWorkerInstances}.
	 */
	@Value("${vertx.springWorker.instances}")
	int springWorkerInstances;

	public static void main(String[] args) {
		SpringApplication.run(DeviceGatherApplication.class, args);
	}

	@EventListener
	public void deployVerticles(ApplicationReadyEvent event) {
		VertxOptions option = new VertxOptions().setWorkerPoolSize(workerPoolSize)
				.setMaxEventLoopExecuteTime(Long.MAX_VALUE);
		Vertx vertx = Vertx.vertx(option);
		vertx.registerVerticleFactory(verticleFactory);
		vertx.deployVerticle(manager);
	}

}
