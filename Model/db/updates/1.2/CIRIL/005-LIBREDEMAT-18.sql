ALTER TABLE request_type ADD COLUMN is_mandatory_document_step bool;
UPDATE request_type SET is_mandatory_document_step = false;
