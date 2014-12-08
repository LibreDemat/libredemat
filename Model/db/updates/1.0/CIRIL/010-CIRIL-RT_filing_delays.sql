update request_type
    set filing_delay = 6
    where filing_delay IS NULL;
