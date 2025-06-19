-- stores
INSERT INTO stores (id, name, address) VALUES
(1, 'Geek電機 渋谷店', '東京都渋谷区Geek坂 1-1-1'),
(2, 'Geek電機 新宿店', '東京都新宿区西Geek 1-1-1'),
(3, 'Geek電機 池袋店', '東京都豊島区GeekShine通り 1-1-1');

-- positions
INSERT INTO positions (id, name) VALUES
(1, '店長'),
(2, '副店長'),
(3, 'マネージャー'),
(4, '一般従業員'),
(5, 'パート・アルバイト');

-- roles
INSERT INTO roles (id, name) VALUES
(1, '管理者'),
(2, '一般');

-- manufacturers
INSERT INTO manufacturers (id, name) VALUES
(1, '三角電機'),
(2, '夕立'),
(3, 'Fanasonic'),
(4, 'マイリス・トーヤマ'),
(5, '虎印'),
(6, '西芝'),
(7, 'DALNUDA'),
(8, 'ダンソン'),
(9, 'gRobot');

-- categories_large
INSERT INTO categories_large (id, name) VALUES
(1, '冷蔵庫、洗濯機、掃除機');

-- categories_middle
INSERT INTO categories_middle (id, category_large_id, name) VALUES
(1, 1, '冷蔵庫・冷凍庫'),
(2, 1, '洗濯機・洗濯乾燥機'),
(3, 1, '掃除機・クリーナー');

-- categories_small
INSERT INTO categories_small (id, category_middle_id, name) VALUES
(1, 1, '冷蔵庫'),
(2, 1, '冷凍庫'),
(3, 1, '保冷・冷温ボックス'),
(4, 1, '製氷機'),
(5, 1, '冷蔵庫関連品'),
(6, 2, 'ドラム式洗濯乾燥機'),
(7, 2, '縦型洗濯機'),
(8, 2, '2槽式洗濯機'),
(9, 2, 'ハンディ・小型洗濯機'),
(10, 2, '衣類乾燥機'),
(11, 2, '洗濯機関連品'),
(12, 2, '衣類乾燥機・関連品'),
(13, 3, 'スティッククリーナー'),
(14, 3, 'サイクロン式掃除機'),
(15, 3, '紙パック式掃除機'),
(16, 3, 'ロボット掃除機'),
(17, 3, 'ハンディクリーナー');

-- products
INSERT INTO products (id, manufacturer_id, category_small_id, name, description) VALUES
(1, 1, 1, 'SR-IKJ-01', 'いい感じの冷蔵庫です'),
(2, 2, 6, 'YDC-DSK-1001', 'いい感じのドラム式選択乾燥機です'),
(3, 8, 14, 'danson-slim-01', 'いい感じのサイクロン式掃除機です');