CREATE TABLE `back_officer`(
	id INTEGER NOT NULL AUTO_INCREMENT,
	level SET("trader", "manager") NOT NULL,
	email VARCHAR(200) NOT NULL,
	password TEXT NOT NULL,
	token TEXT,
	created_at DATETIME DEFAULT NOW(),
	updated_at DATETIME NOT NULL DEFAULT NOW(),
	
	PRIMARY KEY(id)
	
)Engine=InnoDB;


CREATE TABLE `analysis_data`(
	id INTEGER NOT NULL AUTO_INCREMENT,
	identifier TEXT,
	position DOUBLE NOT NULL,
	stop_loss DOUBLE NOT NULL,
	take_profit DOUBLE NOT NULL,
	start_time TEXT NOT NULL,
	end_time TEXT NOT NULL,	
	market VARCHAR(200) NOT NULL,
	timeframe VARCHAR(100) NOT NULL,
	order_type VARCHAR(5) NOT NULL,
	capital DOUBLE NOT NULL,
	profit DOUBLE NOT NULL,
	loss DOUBLE NOT NULL,
	created_at DATETIME DEFAULT NOW(),
	updated_at DATETIME NOT NULL DEFAULT NOW(),
	
	
	PRIMARY KEY(id)
	
)Engine=InnoDB;


CREATE TABLE `market_analysis`(
	id INTEGER NOT NULL AUTO_INCREMENT,
	identifier TEXT,
	market VARCHAR(100),
	total_lot INTEGER NOT NULL,
	price INTEGER NOT NULL,
	max_profit INTEGER NOT NULL,
	max_loss INTEGER NOT NULL,
	analysis_data_id INTEGER NOT NULL,
	published TINYINT DEFAULT 0,
	created_at DATETIME DEFAULT NOW(),
	updated_at DATETIME NOT NULL DEFAULT NOW(),
	
	PRIMARY KEY(id),
	
	CONSTRAINT fk_market_analysis_data
	FOREIGN KEY (analysis_data_id) REFERENCES analysis_data(id)
	
)Engine=InnoDB;

CREATE TABLE `market_order`(
	id INTEGER NOT NULL AUTO_INCREMENT,
	identifier TEXT,
	position DOUBLE NOT NULL,
	order_type VARCHAR(5) NOT NULL,
	lot INTEGER NOT NULL,
	amount INTEGER NOT NULL,
	take_profit INTEGER NOT NULL,
	stop_loss INTEGER NOT NULL,
	market_analysis_id INTEGER NOT NULL,
	member_id INTEGER NOT NULL,
	created_at DATETIME DEFAULT NOW(),
	updated_at DATETIME NOT NULL DEFAULT NOW(),
	
	PRIMARY KEY(id),
	
	CONSTRAINT fk_order_market_analysis
	FOREIGN KEY (market_analysis_id) REFERENCES market_analysis(id)
	
)Engine=InnoDB;


CREATE TABLE `market_order_process`(
	id INTEGER NOT NULL AUTO_INCREMENT,
	identifier TEXT,
	last_position DOUBLE NOT NULL,
	benefit INTEGER NOT NULL,
	status VARCHAR(50) NOT NULL,
	close_date DATETIME,
	market_order_id INTEGER NOT NULL,
	created_at DATETIME DEFAULT NOW(),
	updated_at DATETIME NOT NULL DEFAULT NOW(),
	
	PRIMARY KEY(id),
	
	CONSTRAINT fk_process_order_market
	FOREIGN KEY (market_order_id) REFERENCES market_order(id)
	
)Engine=InnoDB;








































CREATE TABLE `member` (
	id INTEGER NOT NULL AUTO_INCREMENT,
	lastname VARCHAR(50),
	firstname VARCHAR(50),
	email VARCHAR(100),
	point INTEGER NOT NULL DEFAULT 0,
	country VARCHAR(100) NOT NULL,
	grade VARCHAR(100) NOT NULL DEFAULT 'beginner',
	phone VARCHAR(50) NOT NULL,
	password TEXT NOT NULL,
	reference TEXT NOT NULL,
	referenced TEXT NOT NULL,
	created_at DATETIME DEFAULT NOW(),
	updated_at DATETIME NOT NULL DEFAULT NOW(),
	verified TINYINT NOT NULL DEFAULT 0,
	
	PRIMARY KEY(id)

)Engine=InnoDB;


CREATE TABLE `member_verificator` (
	id INTEGER NOT NULL AUTO_INCREMENT,
	identifier TEXT NOT NULL,
	code VARCHAR(7) NOT NULL,
	used TINYINT NOT NULL DEFAULT 0,
	
	created_at DATETIME DEFAULT NOW(),
	updated_at DATETIME NOT NULL DEFAULT NOW(),
	
	member_id INTEGER NOT NULL,
	
	PRIMARY KEY(id),
	
	CONSTRAINT fk_verficator_member
	FOREIGN KEY (member_id) REFERENCES member(id)
)Engine=InnoDB;


CREATE TABLE `member_account`(
	id INTEGER NOT NULL AUTO_INCREMENT,
	amount DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	transaction_code TEXT DEFAULT '',
	created_at DATETIME DEFAULT NOW(),
	updated_at DATETIME NOT NULL DEFAULT NOW(),
	
	member_id INTEGER NOT NULL,
	
	PRIMARY KEY(id),
	CONSTRAINT fk_account_member
	FOREIGN KEY (member_id) REFERENCES member(id)
)Engine=InnoDB;

--- Table for containing the main deposit request on 
--- on the member account
CREATE TABLE `member_deposit_request`(
	id INTEGER NOT NULL AUTO_INCREMENT,
	payment VARCHAR(50) NOT NULL,
	amount DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	amount_before DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	amount_after DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	metadata TEXT,
	date_init DATETIME DEFAULT NOW(),
	filled TINYINT DEFAULT 0,
	extra TEXT,
	member_account_id INTEGER NOT NULL,
	
	PRIMARY KEY(id),
	CONSTRAINT fk_account_deposit
	FOREIGN KEY (member_account_id) REFERENCES member_account(id)
)Engine=InnoDB;


--- Describing a withdrawal request made on
--- the main account
CREATE TABLE `member_withdraw_request`(
	id INTEGER NOT NULL AUTO_INCREMENT,
	payment VARCHAR(50) NOT NULL,
	account_type SET('invest', 'profit', 'sponsor') NOT NULL DEFAULT 'invest',
	amount DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	amount_before DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	amount_after DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	metadata TEXT,
	date_init DATETIME DEFAULT NOW(),
	filled TINYINT DEFAULT 0,
	extra TEXT,
	member_account_id INTEGER NOT NULL,
	
	PRIMARY KEY(id),
	CONSTRAINT fk_account_withdraw
	FOREIGN KEY (member_account_id) REFERENCES member_account(id)
)Engine=InnoDB;

--- ###############################################################################################################
---- [START] describing main account interest payment data informations
--- ###############################################################################################################


--- [ ADDED ] on 08 June 2020 
--- Describing the interest payment account
--- It holds the total amount remaining after payment
--- and making other request on the account
CREATE TABLE `member_account_interest_payment_account`(
	id INTEGER NOT NULL AUTO_INCREMENT,
	amount DECIMAL(10, 2) NOT NULL DEFAULT 0,
	created_at DATETIME DEFAULT NOW(),
	updated_at DATETIME NOT NULL DEFAULT NOW(),
	
	member_account_id INTEGER NOT NULL,
	
	PRIMARY KEY(id),
	CONSTRAINT fk_account_interest_account
	FOREIGN KEY (member_account_id) REFERENCES member_account(id)
)Engine=InnoDB;

--- Describing an interest payment made
--- on the principal account
CREATE TABLE `member_account_interest_payment`(
	id INTEGER NOT NULL AUTO_INCREMENT,
	rate DECIMAL(5, 2)  NOT NULL DEFAULT 0,
	amount_before INTEGER NOT NULL DEFAULT 0,
	amount_after INTEGER NOT NULL DEFAULT 0,
	date_init DATETIME DEFAULT NOW(),
	
	member_account_id INTEGER NOT NULL,
	--- intereste_account_id INTEGER NOT NULL,
	
	PRIMARY KEY(id),
	CONSTRAINT fk_account_interest
	FOREIGN KEY (member_account_id) REFERENCES member_account(id)
)Engine=InnoDB;




--- [ ADDED ] on 08 June 2020 
--- Describing a request for merge the interest amount to the
--- main account
CREATE TABLE `member_account_interest_payment_account_merge_request`(
	id INTEGER NOT NULL AUTO_INCREMENT,
	amount DECIMAL(10, 2) NOT NULL DEFAULT 0,
	amount_before DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	amount_after DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	status SET('pending', 'cancel', 'accepted') DEFAULT 'accepted',
	created_at DATETIME DEFAULT NOW(),
	updated_at DATETIME NOT NULL DEFAULT NOW(),
	
	interest_account_id INTEGER NOT NULL,
	
	PRIMARY KEY(id),
	CONSTRAINT fk_account_interest_account_merge
	FOREIGN KEY (interest_account_id) REFERENCES member_account_interest_payment_account(id)
)Engine=InnoDB;


--- [ ADDED ] on 13 June 2020 
--- Describing a request for a withdrawal action on the interest amount to the
--- main account
CREATE TABLE `member_interest_withdraw_request`(
	id INTEGER NOT NULL AUTO_INCREMENT,
	payment VARCHAR(50) NOT NULL,
	amount DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	amount_before DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	amount_after DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	metadata TEXT,
	created_at DATETIME DEFAULT NOW(),
	filled TINYINT DEFAULT 0,
	
	interest_account_id INTEGER NOT NULL,
	
	PRIMARY KEY(id),
	CONSTRAINT fk_account_interest_account_withdrawal
	FOREIGN KEY (interest_account_id) REFERENCES member_account_interest_payment_account(id)
)Engine=InnoDB;


--- ###############################################################################################################
---- [END] describing main account interest payment data informations
--- ###############################################################################################################






--- ###############################################################################################################
---- [START] describing main account SPONSORSHIP payment data information
--- ###############################################################################################################


--- [ ADDED ] on 12 June 2020 
--- Describing the isponsorship payment account
--- It holds the total amount remaining after payment
--- and making other request on the account
CREATE TABLE `member_sponsorship_payment_account`(
	id INTEGER NOT NULL AUTO_INCREMENT,
	amount DECIMAL(10, 2) NOT NULL DEFAULT 0,
	created_at DATETIME DEFAULT NOW(),
	updated_at DATETIME NOT NULL DEFAULT NOW(),
	
	member_account_id INTEGER NOT NULL,
	
	PRIMARY KEY(id),
	CONSTRAINT fk_account_sponsorship_account
	FOREIGN KEY (member_account_id) REFERENCES member_account(id)
)Engine=InnoDB;

--- Describing an sponsorship payment made
--- on the principal account
CREATE TABLE `member_account_sponsorship_payment`(
	id INTEGER NOT NULL AUTO_INCREMENT,
	amount_before INTEGER NOT NULL DEFAULT 0,
	amount_after INTEGER NOT NULL DEFAULT 0,
	date_init DATETIME DEFAULT NOW(),
	
	member_account_id INTEGER NOT NULL,
	
	PRIMARY KEY(id),
	CONSTRAINT fk_account_sponsorship
	FOREIGN KEY (member_account_id) REFERENCES member_account(id)
)Engine=InnoDB;




--- [ ADDED ] on 13 June 2020 
--- Describing a request for merge the sponsorship amount to the
--- [main account] Tothe interest account
CREATE TABLE `member_sponsorship_payment_merge_request`(
	id INTEGER NOT NULL AUTO_INCREMENT,
	amount DECIMAL(10, 2) NOT NULL DEFAULT 0,
	amount_before DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	amount_after DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	status SET('pending', 'cancel', 'accepted') DEFAULT 'accepted',
	created_at DATETIME DEFAULT NOW(),
	updated_at DATETIME NOT NULL DEFAULT NOW(),
	
	sponsorship_account_id INTEGER NOT NULL,
	
	PRIMARY KEY(id),
	CONSTRAINT fk_account_sponsorship_account_merge
	FOREIGN KEY (sponsorship_account_id) REFERENCES member_sponsorship_payment_account(id)
)Engine=InnoDB;


--- [ ADDED ] on 13 June 2020 
--- Describing a request for a withdrawal action on the sponsorship amount to the
CREATE TABLE `member_sponsorship_payment_withdraw_request`(
	id INTEGER NOT NULL AUTO_INCREMENT,
	payment VARCHAR(50) NOT NULL,
	amount DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	amount_before DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	amount_after DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	metadata TEXT,
	created_at DATETIME DEFAULT NOW(),
	updated_at DATETIME DEFAULT NOW(),
	filled TINYINT DEFAULT 0,
	
	sponsorship_account_id INTEGER NOT NULL,
	
	PRIMARY KEY(id),
	CONSTRAINT fk_account_sponsorship_account_withdrawal
	FOREIGN KEY (sponsorship_account_id) REFERENCES member_sponsorship_payment_account(id)
)Engine=InnoDB;


--- ###############################################################################################################
---- [END] describing main account interest payment data informations
--- ###############################################################################################################






--- ###############################################################################################################
---- [START] Card transaction data information
--- ###############################################################################################################

--- Table for containing a card deposit transaction 
--- on the member account
CREATE TABLE `member_card_transaction`(
	id INTEGER NOT NULL AUTO_INCREMENT,
	amount DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	amount_before DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	amount_after DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	metadata TEXT,
	payment_session_id TEXT,
	identifier TEXT,
	created_at DATETIME DEFAULT NOW(),
	filled TINYINT DEFAULT 0,
	member_account_id INTEGER NOT NULL,
	
	PRIMARY KEY(id),
	CONSTRAINT fk_account_card_transaction
	FOREIGN KEY (member_account_id) REFERENCES member_account(id)
)Engine=InnoDB;

--- ###############################################################################################################
---- [END] Card transaction data information
--- ###############################################################################################################


--- ###############################################################################################################
---- [START] Perfect Money transaction data information
--- ###############################################################################################################

--- Table for containing a card deposit transaction 
--- on the member account
CREATE TABLE `member_perfect_money_transaction`(
	id INTEGER NOT NULL AUTO_INCREMENT,
	amount DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	amount_before DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	amount_after DECIMAL(10, 2) NOT NULL DEFAULT '0.00',
	metadata TEXT,
	identifier TEXT,
	payment_id TEXT,
	created_at DATETIME DEFAULT NOW(),
	filled TINYINT DEFAULT 0,
	member_account_id INTEGER NOT NULL,
	
	PRIMARY KEY(id),
	CONSTRAINT fk_account_perfect_money_transaction
	FOREIGN KEY (member_account_id) REFERENCES member_account(id)
)Engine=InnoDB;

--- ###############################################################################################################
---- [END] Perfect Money transaction data information
--- ###############################################################################################################
















--- SELECT analysis and market analysis
SELECT
market_analysis.id AS m_id,
market_analysis.identifier as m_identifier,
market_analysis.market AS  m_market,
market_analysis.total_lot AS m_total_lot,
market_analysis.price AS m_price,
market_analysis.max_profit AS m_max_profit,
market_analysis.max_loss AS m_max_loss,
market_analysis.analysis_data_id as a_id,

analysis_data.id AS a_id,
analysis_data.identifier AS a_identifier,
analysis_data.position AS a_position,
analysis_data.stop_loss AS a_stop_loss,
analysis_data.take_profit AS a_take_profit,
analysis_data.start_time AS a_start_time,
analysis_data.end_time AS a_end_time,	
analysis_data.market AS a_market,
analysis_data.timeframe AS a_timeframe,
analysis_data.order_type AS a_order_type,
analysis_data.capital AS a_captal,
analysis_data.profit AS a_profit,
analysis_data.loss AS a_loss,
analysis_data.create_at AS a_created_at

FROM market_analysis 
INNER JOIN analysis_data 
ON market_analysis.analysis_data_id=analysis_data.id;


