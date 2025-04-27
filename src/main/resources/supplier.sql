USE stepupdb;

CREATE TABLE IF NOT EXISTS supplier (
    supplier_id INT,
    supplier_name VARCHAR(100) NOT NULL,
    supplier_address VARCHAR(255) NOT NULL,
    supplier_contact BIGINT NOT NULL,
    supplier_email_id VARCHAR(100) NOT NULL,
    CONSTRAINT PRIMARY KEY pkey_supplier(supplier_id)
);

-- Insert 100 sample records into the Supplier table
INSERT INTO Supplier (supplier_id, supplier_name, supplier_address, supplier_email_id) VALUES
(1, 'Wayne Enterprises', '1007 Mountain Dr, Gotham', 9876543211, 'info@wayneenterprises.com'),
(2, 'LexCorp', 'LexCorp Tower, Metropolis', 9876543212, 'contact@lexcorp.com'),
(3, 'Queen Industries', 'Star City Business Park, Star City', 9876543213, 'suppliers@queenindustries.com'),
(4, 'Stark Industries', '10880 Malibu Point, Malibu', 9876543214, 'support@starkindustries.com'),
(5, 'Oscorp', '20 Ingram St, New York', 9876543215, 'info@oscorp.com'),
(6, 'Hammer Industries', '52 Long Island, New York', 9876543216, 'contact@hammerindustries.com'),
(7, 'Pym Technologies', '34 Tech Way, San Francisco', 9876543217, 'service@pymtech.com'),
(8, 'Future Foundation', '4 Yancy Street, New York', 9876543218, 'support@futurefoundation.com'),
(9, 'Daily Planet', '354 West 38th St, Metropolis', 9876543219, 'news@dailyplanet.com'),
(10, 'Stagg Enterprises', 'Empire Heights, Gotham', 9876543220, 'business@staggenterprises.com');
