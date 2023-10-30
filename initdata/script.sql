--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

-- Started on 2023-10-29 22:06:31

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 16399)
-- Name: attendance; Type: TABLE; Schema: public; Owner: postgres
--


CREATE TABLE public.attendance (
    attendance_id bigint NOT NULL,
    created_by character varying(255),
    create_date timestamp without time zone,
    updated_by character varying(255),
    updated_date timestamp without time zone,
    date_log date,
    leave_status character varying(255),
    over_time time without time zone,
    regular_hour time without time zone,
    request_active boolean DEFAULT false,
    time_in time without time zone,
    time_out time without time zone,
    total_work time without time zone,
    signs_id bigint,
    user_id bigint
);


ALTER TABLE public.attendance OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16408)
-- Name: contract; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contract (
    id bigint NOT NULL,
    created_by character varying(255),
    create_date timestamp without time zone,
    updated_by character varying(255),
    updated_date timestamp without time zone,
    contract_name character varying(200) NOT NULL,
    file_name character varying(255) NOT NULL,
    user_id bigint
);


ALTER TABLE public.contract OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16407)
-- Name: contract_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.contract_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.contract_id_seq OWNER TO postgres;

--
-- TOC entry 4947 (class 0 OID 0)
-- Dependencies: 216
-- Name: contract_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.contract_id_seq OWNED BY public.contract.id;


--
-- TOC entry 218 (class 1259 OID 16416)
-- Name: department; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.department (
    department_id bigint NOT NULL,
    created_by character varying(255),
    create_date timestamp without time zone,
    updated_by character varying(255),
    updated_date timestamp without time zone,
    department_name character varying(255)
);


ALTER TABLE public.department OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 16504)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16424)
-- Name: holiday; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.holiday (
    holiday_id integer NOT NULL,
    end_date date,
    holiday_name character varying(100) NOT NULL,
    start_date date
);


ALTER TABLE public.holiday OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16423)
-- Name: holiday_holiday_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.holiday_holiday_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.holiday_holiday_id_seq OWNER TO postgres;

--
-- TOC entry 4948 (class 0 OID 0)
-- Dependencies: 219
-- Name: holiday_holiday_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.holiday_holiday_id_seq OWNED BY public.holiday.holiday_id;


--
-- TOC entry 221 (class 1259 OID 16430)
-- Name: log_check_in_out; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.log_check_in_out (
    log_checkinout_id bigint NOT NULL,
    created_by character varying(255),
    create_date timestamp without time zone,
    updated_by character varying(255),
    updated_date timestamp without time zone,
    badge_number character varying(255),
    date_check date,
    time_check time without time zone,
    user_id bigint
);


ALTER TABLE public.log_check_in_out OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16437)
-- Name: log_in_late_out_early; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.log_in_late_out_early (
    log_inlateoutearly_id bigint NOT NULL,
    created_by character varying(255),
    create_date timestamp without time zone,
    updated_by character varying(255),
    updated_date timestamp without time zone,
    employee_id character varying(255),
    io_kind character varying(255),
    reason character varying(255),
    date_check date,
    duration time without time zone,
    time_end time without time zone,
    time_start time without time zone,
    user_id bigint
);


ALTER TABLE public.log_in_late_out_early OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16445)
-- Name: position; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."position" (
    position_id bigint NOT NULL,
    created_by character varying(255),
    create_date timestamp without time zone,
    updated_by character varying(255),
    updated_date timestamp without time zone,
    position_name character varying(200) NOT NULL
);


ALTER TABLE public."position" OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16444)
-- Name: position_position_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.position_position_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.position_position_id_seq OWNER TO postgres;

--
-- TOC entry 4949 (class 0 OID 0)
-- Dependencies: 223
-- Name: position_position_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.position_position_id_seq OWNED BY public."position".position_id;


--
-- TOC entry 225 (class 1259 OID 16453)
-- Name: position_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.position_roles (
    position_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.position_roles OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 16459)
-- Name: request; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.request (
    request_id bigint NOT NULL,
    created_by character varying(255),
    create_date timestamp without time zone,
    updated_by character varying(255),
    updated_date timestamp without time zone,
    accept_at date,
    accept_by bigint,
    end_date date,
    end_time time without time zone,
    note character varying(255),
    content character varying(300) NOT NULL,
    request_title character varying(100) NOT NULL,
    start_date date,
    start_time time without time zone,
    status integer NOT NULL,
    request_type_id integer NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.request OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 16468)
-- Name: request_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.request_category (
    request_category_id integer NOT NULL,
    request_category_name character varying(100) NOT NULL
);


ALTER TABLE public.request_category OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16467)
-- Name: request_category_request_category_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.request_category_request_category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.request_category_request_category_id_seq OWNER TO postgres;

--
-- TOC entry 4950 (class 0 OID 0)
-- Dependencies: 228
-- Name: request_category_request_category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.request_category_request_category_id_seq OWNED BY public.request_category.request_category_id;


--
-- TOC entry 226 (class 1259 OID 16458)
-- Name: request_request_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.request_request_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.request_request_id_seq OWNER TO postgres;

--
-- TOC entry 4951 (class 0 OID 0)
-- Dependencies: 226
-- Name: request_request_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.request_request_id_seq OWNED BY public.request.request_id;


--
-- TOC entry 231 (class 1259 OID 16475)
-- Name: request_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.request_type (
    request_type_id integer NOT NULL,
    replacement_person character varying(100),
    replacement_work character varying(100),
    request_type_name character varying(100) NOT NULL,
    request_category_id integer NOT NULL
);


ALTER TABLE public.request_type OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 16474)
-- Name: request_type_request_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.request_type_request_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.request_type_request_type_id_seq OWNER TO postgres;

--
-- TOC entry 4952 (class 0 OID 0)
-- Dependencies: 230
-- Name: request_type_request_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.request_type_request_type_id_seq OWNED BY public.request_type.request_type_id;


--
-- TOC entry 233 (class 1259 OID 16482)
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
    id bigint NOT NULL,
    role_name character varying(200) NOT NULL
);


ALTER TABLE public.role OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 16481)
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.role_id_seq OWNER TO postgres;

--
-- TOC entry 4953 (class 0 OID 0)
-- Dependencies: 232
-- Name: role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.role_id_seq OWNED BY public.role.id;


--
-- TOC entry 234 (class 1259 OID 16488)
-- Name: sign; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sign (
    sign_id bigint NOT NULL,
    sign_name character varying(20)
);


ALTER TABLE public.sign OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 16494)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    created_by character varying(255),
    create_date timestamp without time zone,
    updated_by character varying(255),
    updated_date timestamp without time zone,
    address character varying(255),
    birth_day date,
    email character varying(200) NOT NULL,
    end_work date,
    full_name character varying(200) NOT NULL,
    gender integer,
    password character varying(255) NOT NULL,
    phone character varying(255),
    start_work date,
    status integer,
    user_code character varying(255),
    user_image character varying(255),
    user_name character varying(255),
    department_id bigint NOT NULL,
    position_id bigint
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 16493)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 4954 (class 0 OID 0)
-- Dependencies: 235
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 4749 (class 2604 OID 16411)
-- Name: contract id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contract ALTER COLUMN id SET DEFAULT nextval('public.contract_id_seq'::regclass);


--
-- TOC entry 4750 (class 2604 OID 16427)
-- Name: holiday holiday_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.holiday ALTER COLUMN holiday_id SET DEFAULT nextval('public.holiday_holiday_id_seq'::regclass);


--
-- TOC entry 4751 (class 2604 OID 16448)
-- Name: position position_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."position" ALTER COLUMN position_id SET DEFAULT nextval('public.position_position_id_seq'::regclass);


--
-- TOC entry 4752 (class 2604 OID 16462)
-- Name: request request_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request ALTER COLUMN request_id SET DEFAULT nextval('public.request_request_id_seq'::regclass);


--
-- TOC entry 4753 (class 2604 OID 16471)
-- Name: request_category request_category_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request_category ALTER COLUMN request_category_id SET DEFAULT nextval('public.request_category_request_category_id_seq'::regclass);


--
-- TOC entry 4754 (class 2604 OID 16478)
-- Name: request_type request_type_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request_type ALTER COLUMN request_type_id SET DEFAULT nextval('public.request_type_request_type_id_seq'::regclass);


--
-- TOC entry 4755 (class 2604 OID 16485)
-- Name: role id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role ALTER COLUMN id SET DEFAULT nextval('public.role_id_seq'::regclass);


--
-- TOC entry 4756 (class 2604 OID 16497)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 4758 (class 2606 OID 16406)
-- Name: attendance attendance_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.attendance
    ADD CONSTRAINT attendance_pkey PRIMARY KEY (attendance_id);


--
-- TOC entry 4760 (class 2606 OID 16415)
-- Name: contract contract_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contract
    ADD CONSTRAINT contract_pkey PRIMARY KEY (id);


--
-- TOC entry 4762 (class 2606 OID 16422)
-- Name: department department_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.department
    ADD CONSTRAINT department_pkey PRIMARY KEY (department_id);


--
-- TOC entry 4764 (class 2606 OID 16429)
-- Name: holiday holiday_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.holiday
    ADD CONSTRAINT holiday_pkey PRIMARY KEY (holiday_id);


--
-- TOC entry 4766 (class 2606 OID 16436)
-- Name: log_check_in_out log_check_in_out_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_check_in_out
    ADD CONSTRAINT log_check_in_out_pkey PRIMARY KEY (log_checkinout_id);


--
-- TOC entry 4768 (class 2606 OID 16443)
-- Name: log_in_late_out_early log_in_late_out_early_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_in_late_out_early
    ADD CONSTRAINT log_in_late_out_early_pkey PRIMARY KEY (log_inlateoutearly_id);


--
-- TOC entry 4770 (class 2606 OID 16452)
-- Name: position position_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."position"
    ADD CONSTRAINT position_pkey PRIMARY KEY (position_id);


--
-- TOC entry 4772 (class 2606 OID 16457)
-- Name: position_roles position_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.position_roles
    ADD CONSTRAINT position_roles_pkey PRIMARY KEY (position_id, role_id);


--
-- TOC entry 4776 (class 2606 OID 16473)
-- Name: request_category request_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request_category
    ADD CONSTRAINT request_category_pkey PRIMARY KEY (request_category_id);


--
-- TOC entry 4774 (class 2606 OID 16466)
-- Name: request request_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request
    ADD CONSTRAINT request_pkey PRIMARY KEY (request_id);


--
-- TOC entry 4778 (class 2606 OID 16480)
-- Name: request_type request_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request_type
    ADD CONSTRAINT request_type_pkey PRIMARY KEY (request_type_id);


--
-- TOC entry 4780 (class 2606 OID 16487)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- TOC entry 4782 (class 2606 OID 16492)
-- Name: sign sign_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sign
    ADD CONSTRAINT sign_pkey PRIMARY KEY (sign_id);


--
-- TOC entry 4784 (class 2606 OID 16503)
-- Name: users uk_6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- TOC entry 4786 (class 2606 OID 16501)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4790 (class 2606 OID 16520)
-- Name: log_check_in_out fk3348gbb1ck4uksbe3hj0cnf5d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_check_in_out
    ADD CONSTRAINT fk3348gbb1ck4uksbe3hj0cnf5d FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4796 (class 2606 OID 16550)
-- Name: request_type fk5hvutd3vg845f5iirsuk8wtc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request_type
    ADD CONSTRAINT fk5hvutd3vg845f5iirsuk8wtc FOREIGN KEY (request_category_id) REFERENCES public.request_category(request_category_id);


--
-- TOC entry 4797 (class 2606 OID 16555)
-- Name: users fk7vgyxapb64uaodvvpvpubeert; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk7vgyxapb64uaodvvpvpubeert FOREIGN KEY (department_id) REFERENCES public.department(department_id);


--
-- TOC entry 4798 (class 2606 OID 16560)
-- Name: users fkay81wkdumu7athhw45e1l1lln; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fkay81wkdumu7athhw45e1l1lln FOREIGN KEY (position_id) REFERENCES public."position"(position_id);


--
-- TOC entry 4794 (class 2606 OID 16545)
-- Name: request fkg03bldv86pfuboqfefx48p6u3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request
    ADD CONSTRAINT fkg03bldv86pfuboqfefx48p6u3 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4789 (class 2606 OID 16515)
-- Name: contract fki6rphdb5rpnqnrp5twyk83jao; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contract
    ADD CONSTRAINT fki6rphdb5rpnqnrp5twyk83jao FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4795 (class 2606 OID 16540)
-- Name: request fkibmr315gqv6g75nhasnydst5w; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request
    ADD CONSTRAINT fkibmr315gqv6g75nhasnydst5w FOREIGN KEY (request_type_id) REFERENCES public.request_type(request_type_id);


--
-- TOC entry 4787 (class 2606 OID 16510)
-- Name: attendance fkjcaqd29v2qy723owsdah2t8vx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.attendance
    ADD CONSTRAINT fkjcaqd29v2qy723owsdah2t8vx FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4788 (class 2606 OID 16505)
-- Name: attendance fkmrksvah1c131dhkb8g4wyx4hi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.attendance
    ADD CONSTRAINT fkmrksvah1c131dhkb8g4wyx4hi FOREIGN KEY (signs_id) REFERENCES public.sign(sign_id);


--
-- TOC entry 4792 (class 2606 OID 16530)
-- Name: position_roles fkns0g5lbet1viwpnyaffh8m51k; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.position_roles
    ADD CONSTRAINT fkns0g5lbet1viwpnyaffh8m51k FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- TOC entry 4791 (class 2606 OID 16525)
-- Name: log_in_late_out_early fkpvam1mwq724d82ntch7xntmg9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_in_late_out_early
    ADD CONSTRAINT fkpvam1mwq724d82ntch7xntmg9 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4793 (class 2606 OID 16535)
-- Name: position_roles fkpy5tejqwi1y43h4a0ei86v94c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.position_roles
    ADD CONSTRAINT fkpy5tejqwi1y43h4a0ei86v94c FOREIGN KEY (position_id) REFERENCES public."position"(position_id);


-- Completed on 2023-10-29 22:06:32

--
-- PostgreSQL database dump complete
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

-- Started on 2023-10-29 22:08:24

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4936 (class 0 OID 16416)
-- Dependencies: 218
-- Data for Name: department; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.department (department_id, created_by, create_date, updated_by, updated_date, department_name) FROM stdin;
\.


--
-- TOC entry 4942 (class 0 OID 16445)
-- Dependencies: 224
-- Data for Name: position; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."position" (position_id, created_by, create_date, updated_by, updated_date, position_name) FROM stdin;
\.


--
-- TOC entry 4952 (class 0 OID 16488)
-- Dependencies: 234
-- Data for Name: sign; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sign (sign_id, sign_name) FROM stdin;
\.


--
-- TOC entry 4954 (class 0 OID 16494)
-- Dependencies: 236
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, created_by, create_date, updated_by, updated_date, address, birth_day, email, end_work, full_name, gender, password, phone, start_work, status, user_code, user_image, user_name, department_id, position_id) FROM stdin;
\.


--
-- TOC entry 4933 (class 0 OID 16399)
-- Dependencies: 215
-- Data for Name: attendance; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.attendance (attendance_id, created_by, create_date, updated_by, updated_date, date_log, leave_status, over_time, regular_hour, request_active, time_in, time_out, total_work, signs_id, user_id) FROM stdin;
\.


--
-- TOC entry 4935 (class 0 OID 16408)
-- Dependencies: 217
-- Data for Name: contract; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.contract (id, created_by, create_date, updated_by, updated_date, contract_name, file_name, user_id) FROM stdin;
\.


--
-- TOC entry 4938 (class 0 OID 16424)
-- Dependencies: 220
-- Data for Name: holiday; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.holiday (holiday_id, end_date, holiday_name, start_date) FROM stdin;
\.


--
-- TOC entry 4939 (class 0 OID 16430)
-- Dependencies: 221
-- Data for Name: log_check_in_out; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.log_check_in_out (log_checkinout_id, created_by, create_date, updated_by, updated_date, badge_number, date_check, time_check, user_id) FROM stdin;
\.


--
-- TOC entry 4940 (class 0 OID 16437)
-- Dependencies: 222
-- Data for Name: log_in_late_out_early; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.log_in_late_out_early (log_inlateoutearly_id, created_by, create_date, updated_by, updated_date, employee_id, io_kind, reason, date_check, duration, time_end, time_start, user_id) FROM stdin;
\.


--
-- TOC entry 4951 (class 0 OID 16482)
-- Dependencies: 233
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.role (id, role_name) FROM stdin;
\.


--
-- TOC entry 4943 (class 0 OID 16453)
-- Dependencies: 225
-- Data for Name: position_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.position_roles (position_id, role_id) FROM stdin;
\.


--
-- TOC entry 4947 (class 0 OID 16468)
-- Dependencies: 229
-- Data for Name: request_category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.request_category (request_category_id, request_category_name) FROM stdin;
\.


--
-- TOC entry 4949 (class 0 OID 16475)
-- Dependencies: 231
-- Data for Name: request_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.request_type (request_type_id, replacement_person, replacement_work, request_type_name, request_category_id) FROM stdin;
\.


--
-- TOC entry 4945 (class 0 OID 16459)
-- Dependencies: 227
-- Data for Name: request; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.request (request_id, created_by, create_date, updated_by, updated_date, accept_at, accept_by, end_date, end_time, note, content, request_title, start_date, start_time, status, request_type_id, user_id) FROM stdin;
\.


--
-- TOC entry 4961 (class 0 OID 0)
-- Dependencies: 216
-- Name: contract_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.contract_id_seq', 1, false);


--
-- TOC entry 4962 (class 0 OID 0)
-- Dependencies: 237
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);


--
-- TOC entry 4963 (class 0 OID 0)
-- Dependencies: 219
-- Name: holiday_holiday_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.holiday_holiday_id_seq', 1, false);


--
-- TOC entry 4964 (class 0 OID 0)
-- Dependencies: 223
-- Name: position_position_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.position_position_id_seq', 1, false);


--
-- TOC entry 4965 (class 0 OID 0)
-- Dependencies: 228
-- Name: request_category_request_category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.request_category_request_category_id_seq', 1, false);


--
-- TOC entry 4966 (class 0 OID 0)
-- Dependencies: 226
-- Name: request_request_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.request_request_id_seq', 1, false);


--
-- TOC entry 4967 (class 0 OID 0)
-- Dependencies: 230
-- Name: request_type_request_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.request_type_request_type_id_seq', 1, false);


--
-- TOC entry 4968 (class 0 OID 0)
-- Dependencies: 232
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.role_id_seq', 1, false);


--
-- TOC entry 4969 (class 0 OID 0)
-- Dependencies: 235
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 1, false);


-- Completed on 2023-10-29 22:08:25

--
-- PostgreSQL database dump complete
--

INSERT INTO public.role(
     role_name)
VALUES ('ROLE_ADMIN');
INSERT INTO public.role(
     role_name)
VALUES ('ROLE_USER');