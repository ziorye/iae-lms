INSERT INTO `collection` (`id`, `title`, `title_translation`, `slug`, `type`, `content`, `video`, `duration`, `cover`, `description`, `published`, `free`, `price`, `view_count`, `seo_title`, `seo_description`, `completed`, `proofread`, `user_id`, `created_at`, `updated_at`, `deleted_at`)
VALUES
    (3, 'Docker 快速入门视频', NULL, 'docker-get-started__video', 'video', NULL, NULL, 1483, 'https://iae-img.oss-cn-shenzhen.aliyuncs.com/iae-proofread/upload/cover/collection/G8g9oBCZUz1628239518.png', '本套视频是在阅读 Docker 官方入门文档的过程中录制的，可视为 Docker 官方入门文档的不完全翻译视频版本。介绍了什么是容器、什么是镜像；以及如何创建镜像、运行容器、构建镜像最佳实践等 Docker 基础知识。', 1, 1, 0.00, 1, NULL, NULL, 1, 0, 1, now(), NULL, NULL);


INSERT INTO `section` (`id`, `slug`, `title`, `title_translation`, `description`, `sort_order`, `collection_id`, `created_at`, `updated_at`, `deleted_at`)
VALUES
    (9, NULL, '视频列表', NULL, NULL, 1, 3, now(), NULL, NULL);


INSERT INTO `lecture` (`id`, `title`, `title_translation`, `slug`, `content`, `video`, `video_id`, `duration`, `description`, `description_translation`, `sort_order`, `published`, `free`, `requires_login`, `section_id`, `collection_id`, `cover`, `created_at`, `updated_at`, `deleted_at`)
VALUES
    (21, '安装 Docker', NULL, 'docker-install__video', '安装并启动 Docker', NULL, 'de8a8c055adc4e619b78edffac3a16f1', 76, NULL, NULL, 1, 1, 1, 0, 9, 3, NULL, now(), NULL, NULL),
    (22, '运行第一个 Docker 容器', NULL, 'docker-getting-started__video', '使用 docker run 命令运行第一个 Docker 容器', NULL, 'af2858ab85134d89b1e76bdb10ff36ad', 153, NULL, NULL, 2, 1, 1, 0, 9, 3, NULL, now(), NULL, NULL),
    (23, '创建自己的 Docker 镜像', NULL, 'our-docker-image__video', '为自己的应用程序创建 Docker 镜像', NULL, '99621c5062e0419dbff5183e3c18b043', 158, NULL, NULL, 3, 1, 1, 0, 9, 3, NULL, now(), NULL, NULL),
    (24, '更新应用并重新构建 Docker 镜像', NULL, 'updating-our-dockder-image__video', '更新自己的应用程序源码，并重新执行 Docker 镜像构建镜像命令', NULL, 'c6bc8c9ab289441f81557e678d9422f1', 123, NULL, NULL, 4, 1, 1, 0, 9, 3, NULL, now(), NULL, NULL),
    (25, '分享自己创建的 Docker 镜像', NULL, 'sharing-our-docker-image__video', '将镜像推送到 Docker Hub，分享自己创建的 Docker 镜像', NULL, 'd7090947bb4d40f1bd9f280edc868cb1', 127, NULL, NULL, 5, 1, 1, 0, 9, 3, NULL, now(), NULL, NULL),
    (26, '使用 Docker 命名卷 named volume 持久化数据', NULL, 'docker-named-volume__video', '使用 Docker 卷类型之一：命名卷 named volume 持久化容器应用数据', NULL, '81003eed55a445f0bccb8f390e4127f1', 159, NULL, NULL, 6, 1, 1, 0, 9, 3, NULL, now(), NULL, NULL),
    (27, '使用 bind mounts 快速搭建本地开发环境', NULL, 'docker-bind-mounts__video', '使用另一种 Docker 卷类型：bind mounts 将主机上的源码挂载到容器中，以便快速搭建开发环境', NULL, '11138ca6de93469db594b1d719c66106', 141, NULL, NULL, 7, 1, 1, 0, 9, 3, NULL, now(), NULL, NULL),
    (28, 'Docker 多容器应用 Multi-Container Apps', NULL, 'docker-multi-container-apps__video', '启动多个 Docker 容器，并连接到相同的网络上，组成同一个应用组', NULL, '44f14b63e838413c853e04a7e71044e2', 198, NULL, NULL, 8, 1, 1, 0, 9, 3, NULL, now(), NULL, NULL),
    (29, '使用 Docker Compose', NULL, 'using-docker-compose__video', 'Docker Compose 是一个用来定义和运行多容器 Docker 应用程序的工具；使用它，只需执行一条命令，就可以根据配置自动创建并启动所有服务', NULL, 'e18561027d404f3eb244da738e90f7a7', 158, NULL, NULL, 9, 1, 1, 0, 9, 3, NULL, now(), NULL, NULL),
    (30, '构建 Docker 镜像的最佳实践', NULL, 'docker-image-building-best-practices__video', '构建 Docker 镜像的最佳实践：安全扫描、镜像分层、层缓存、多阶段构建、等等', NULL, '867fcf07780144808d60b05525e4dbc7', 190, NULL, NULL, 10, 1, 1, 0, 9, 3, NULL, now(), NULL, NULL);
