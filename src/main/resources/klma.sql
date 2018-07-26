CREATE DATABASE klma;
USE klma;

CREATE TABLE plan (
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  total_words INT NOT NULL COMMENT '每日单词总个数',
  new_words INT NOT NULL COMMENT '新词个数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE user (
  id CHAR(11) PRIMARY KEY COMMENT '电话号码作为账号',
  password CHAR(32) NOT NULL COMMENT '密码',
  nickname VARCHAR(32) NOT NULL COMMENT '昵称',
  salt CHAR(8) NOT NULL COMMENT '加密盐',
  plan_id INT UNSIGNED COMMENT '学习计划',
  current_lexicon_id INT UNSIGNED COMMENT '当前背诵的词库',
  FOREIGN KEY (plan_id) REFERENCES plan(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE category (
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(10) NOT NULL COMMENT '一级目录名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE person_lexicon (
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  user_id CHAR(11) NOT NULL,
  category_id INT UNSIGNED NOT NULL COMMENT '一级目录标识',
  name VARCHAR(32) NOT NULL COMMENT '个人词库名称',
  FOREIGN KEY (user_id) REFERENCES user(id),
  FOREIGN KEY (category_id) REFERENCES category(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# CREATE TABLE sub_category (
#   id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
#   category_id INT UNSIGNED NOT NULL COMMENT '一级目录标识',
#   name VARCHAR(32) NOT NULL COMMENT '二级目录名称',
#   FOREIGN KEY (category_id) REFERENCES category(id)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE word (
  word VARCHAR(32) COMMENT '单词',
  person_lexicon_id INT UNSIGNED COMMENT '个人词库标识',
  chinese_meaning VARCHAR(32) NOT NULL COMMENT '中文含义',
  sound_url VARCHAR(16) COMMENT '音源',
  soundmark VARCHAR(16) COMMENT '音标',
  example_sentence LONGTEXT COMMENT '例句',
#   learn_times INT NOT NULL DEFAULT 0 COMMENT '学习次数',
#   coefficient DECIMAL(6, 4) DEFAULT 0 COMMENT '上次的学习系数',
  coefficient DECIMAL(6, 4) DEFAULT 0 COMMENT '学习系数',
  last_coefficient DECIMAL(6, 4) DEFAULT 0 COMMENT '上次学习系数',
  last_date DATE COMMENT '最后一次记忆时间',
  FOREIGN KEY (person_lexicon_id) REFERENCES person_lexicon(id),
  PRIMARY KEY (person_lexicon_id, word)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# CREATE TABLE word_record (
#   word VARCHAR(32) NOT NULL COMMENT '单词',
#   lexicon_id INT UNSIGNED NOT NULL COMMENT '词库名称',
#   ratio DECIMAL(6, 4) NOT NULL COMMENT '比值 = 认识/全部',
#   date DATE NOT NULL COMMENT '当天日期',
#   FOREIGN KEY (word) REFERENCES word (word),
#   FOREIGN KEY (lexicon_id) REFERENCES person_lexicon (id)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE recite_record (
  user_id CHAR(11) NOT NULL COMMENT '用户ID',
  date DATE NOT NULL COMMENT '当天日期',
  total_words INT NOT NULL,
  new_words INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user (id),
  PRIMARY KEY (user_id, date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# select * from word where person_lexicon_id = #{personLexiconId} and coefficient > 0 and coefficient != 10 and date != #{date} order by coefficient desc limit #{num}
insert into person_lexicon (category_id, name, user_id) values (3, 'Other', '12345678910')