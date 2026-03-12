CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE habits (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        target_days_per_week INT NOT NULL,
                        user_id INT NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE habit_logs (
                            id SERIAL PRIMARY KEY,
                            habit_id INT NOT NULL,
                            log_date DATE NOT NULL,
                            completed BOOLEAN NOT NULL,
                            FOREIGN KEY (habit_id) REFERENCES habits(id),
                            UNIQUE (habit_id, log_date)
);