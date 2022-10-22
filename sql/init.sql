-- ----------------------------
-- Table structure for car
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `car_model` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '车型',
  `stock` int(11) NOT NULL COMMENT '库存',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` int(11) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` bigint(20) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of car
-- ----------------------------
INSERT INTO `car` VALUES (1, 'Toyota Camry', 2, -1, 1666423787904, -1, 1666423787904);
INSERT INTO `car` VALUES (2, 'BMW 650', 2, -1, 1666423787904, -1, 1666423787904);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `salt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '盐',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` int(11) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` bigint(20) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'chenqingliang', 'a5bc63cd07ce6e4cf501e38a71557812', 'chen', '51881362', -1, 1666423787904, -1, 1666423787904);
INSERT INTO `user` VALUES (2, 'chenqingliang1', '9685a90f6f3b6440ffa76ceab043382c', 'chen', '94886896', -1, 1666424036153, -1, 1666424036153);
INSERT INTO `user` VALUES (3, 'chenqingliang2', '15fa934ecb4a90f1e38b381b86e11302', 'chen', '95647655', -1, 1666424097696, -1, 1666424097696);
INSERT INTO `user` VALUES (4, 'chenqingliang3', 'ea6e262c04e9b2df5e9680214a5d5724', 'chen', '64153647', -1, 1666424264283, -1, 1666424264283);
INSERT INTO `user` VALUES (5, 'chenqingliang4', '6a3e7b45fa7e40094cd84783ffd66867', 'chen', '54692516', -1, 1666424936952, -1, 1666424936952);
INSERT INTO `user` VALUES (6, 'chenqingliang5', '32d8555930e8a562c3d5e2a9ab060122', 'chen', '35653193', -1, 1666425546231, -1, 1666425546231);
INSERT INTO `user` VALUES (7, 'chenqingliang6', 'dbbd9b0422f2c609960254b76776d24c', 'chen', '80813296', -1, 1666425687025, -1, 1666425687025);
INSERT INTO `user` VALUES (17, 'chen1666447766048', 'e116bcbe688909bc26be712d53f76ecd', 'chen1666447766048', '81058435', -1, 1666447766217, -1, 1666447766217);
INSERT INTO `user` VALUES (18, 'chen1666447859287', '15f3c6d38ba6a468bf7993f9ffec7046', 'chen1666447859287', '40747857', -1, 1666447859431, -1, 1666447859431);
INSERT INTO `user` VALUES (19, 'chen1', '7d320b95596c05db29fc3834236d65d6', 'chen1', '53604761', -1, 1666448037536, -1, 1666448037536);

-- ----------------------------
-- Table structure for reservation
-- ----------------------------
DROP TABLE IF EXISTS `reservation`;
CREATE TABLE `reservation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `car_id` int(11) NOT NULL COMMENT '车型id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `start_time` datetime(0) NOT NULL COMMENT '开始时间',
  `end_time` datetime(0) NOT NULL COMMENT '结束时间',
  `return_time` datetime(0) NULL DEFAULT NULL COMMENT '实际归还时间',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` int(11) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` bigint(20) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_car_id`(`car_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reservation
-- ----------------------------
INSERT INTO `reservation` VALUES (1, 1, 1, '2022-10-22 20:15:30', '2022-10-22 22:10:10', '2022-10-22 21:26:06', 'FINISHED', 1, 1666442012213, 1, 1666445166682);
INSERT INTO `reservation` VALUES (2, 4, 6, '2022-10-22 21:30:00', '2022-10-23 00:00:00', NULL, 'CREATED', 6, 1666445521616, 6, 1666445521616);