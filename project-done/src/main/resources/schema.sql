ALTER TABLE medication_info
ADD FOREIGN KEY (id) REFERENCES drone_info(id);
