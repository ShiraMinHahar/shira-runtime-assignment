CREATE TABLE runtime_policies (
                                  policy_id SERIAL PRIMARY KEY,
                                  policy_name VARCHAR(255) UNIQUE NOT NULL,
                                  author VARCHAR(255) NOT NULL,
                                  controls JSONB NOT NULL,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Grant privileges to a user
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE runtime_policies TO shira;
