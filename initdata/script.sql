--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

-- Started on 2023-11-06 21:54:34

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


-- TOC entry 4964 (class 1262 OID 16596)
-- Name: ems; Type: DATABASE; Schema: -; Owner: postgres
--




ALTER DATABASE ems OWNER TO postgres;

\connect ems

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
-- TOC entry 215 (class 1259 OID 16597)
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
-- TOC entry 217 (class 1259 OID 16606)
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
-- TOC entry 216 (class 1259 OID 16605)
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
-- TOC entry 4965 (class 0 OID 0)
-- Dependencies: 216
-- Name: contract_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.contract_id_seq OWNED BY public.contract.id;


--
-- TOC entry 218 (class 1259 OID 16614)
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
-- TOC entry 240 (class 1259 OID 16716)
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
-- TOC entry 220 (class 1259 OID 16622)
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
-- TOC entry 219 (class 1259 OID 16621)
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
-- TOC entry 4966 (class 0 OID 0)
-- Dependencies: 219
-- Name: holiday_holiday_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.holiday_holiday_id_seq OWNED BY public.holiday.holiday_id;


--
-- TOC entry 221 (class 1259 OID 16628)
-- Name: log_attendance_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.log_attendance_history (
                                               attendance_history_id bigint NOT NULL,
                                               reason character varying(255),
                                               updated_by character varying(255),
                                               updated_date character varying(255),
                                               attendance_id bigint,
                                               new_signs_id bigint,
                                               old_signs_id bigint
);


ALTER TABLE public.log_attendance_history OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16635)
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
-- TOC entry 223 (class 1259 OID 16642)
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
-- TOC entry 225 (class 1259 OID 16650)
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
-- TOC entry 224 (class 1259 OID 16649)
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
-- TOC entry 4967 (class 0 OID 0)
-- Dependencies: 224
-- Name: position_position_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.position_position_id_seq OWNED BY public."position".position_id;


--
-- TOC entry 226 (class 1259 OID 16658)
-- Name: position_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.position_roles (
                                       position_id bigint NOT NULL,
                                       role_id bigint NOT NULL
);


ALTER TABLE public.position_roles OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16664)
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
-- TOC entry 230 (class 1259 OID 16673)
-- Name: request_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.request_category (
                                         request_category_id integer NOT NULL,
                                         request_category_name character varying(100) NOT NULL
);


ALTER TABLE public.request_category OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 16672)
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
-- TOC entry 4968 (class 0 OID 0)
-- Dependencies: 229
-- Name: request_category_request_category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.request_category_request_category_id_seq OWNED BY public.request_category.request_category_id;


--
-- TOC entry 227 (class 1259 OID 16663)
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
-- TOC entry 4969 (class 0 OID 0)
-- Dependencies: 227
-- Name: request_request_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.request_request_id_seq OWNED BY public.request.request_id;


--
-- TOC entry 232 (class 1259 OID 16680)
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
-- TOC entry 231 (class 1259 OID 16679)
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
-- TOC entry 4970 (class 0 OID 0)
-- Dependencies: 231
-- Name: request_type_request_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.request_type_request_type_id_seq OWNED BY public.request_type.request_type_id;


--
-- TOC entry 234 (class 1259 OID 16687)
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
                             id bigint NOT NULL,
                             role_name character varying(200) NOT NULL
);


ALTER TABLE public.role OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 16686)
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
-- TOC entry 4971 (class 0 OID 0)
-- Dependencies: 233
-- Name: role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.role_id_seq OWNED BY public.role.id;


--
-- TOC entry 235 (class 1259 OID 16693)
-- Name: sign; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sign (
                             sign_id bigint NOT NULL,
                             sign_name character varying(20)
);


ALTER TABLE public.sign OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 16699)
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
                              day_off integer,
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
-- TOC entry 236 (class 1259 OID 16698)
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
-- TOC entry 4972 (class 0 OID 0)
-- Dependencies: 236
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 239 (class 1259 OID 16708)
-- Name: working_time; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.working_time (
                                     working_time_id integer NOT NULL,
                                     end_time time without time zone,
                                     start_time time without time zone,
                                     working_time_name character varying(100) NOT NULL
);


ALTER TABLE public.working_time OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 16707)
-- Name: working_time_working_time_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.working_time_working_time_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.working_time_working_time_id_seq OWNER TO postgres;

--
-- TOC entry 4973 (class 0 OID 0)
-- Dependencies: 238
-- Name: working_time_working_time_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.working_time_working_time_id_seq OWNED BY public.working_time.working_time_id;


--
-- TOC entry 4758 (class 2604 OID 16609)
-- Name: contract id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contract ALTER COLUMN id SET DEFAULT nextval('public.contract_id_seq'::regclass);


--
-- TOC entry 4759 (class 2604 OID 16625)
-- Name: holiday holiday_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.holiday ALTER COLUMN holiday_id SET DEFAULT nextval('public.holiday_holiday_id_seq'::regclass);


--
-- TOC entry 4760 (class 2604 OID 16653)
-- Name: position position_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."position" ALTER COLUMN position_id SET DEFAULT nextval('public.position_position_id_seq'::regclass);


--
-- TOC entry 4761 (class 2604 OID 16667)
-- Name: request request_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request ALTER COLUMN request_id SET DEFAULT nextval('public.request_request_id_seq'::regclass);


--
-- TOC entry 4762 (class 2604 OID 16676)
-- Name: request_category request_category_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request_category ALTER COLUMN request_category_id SET DEFAULT nextval('public.request_category_request_category_id_seq'::regclass);


--
-- TOC entry 4763 (class 2604 OID 16683)
-- Name: request_type request_type_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request_type ALTER COLUMN request_type_id SET DEFAULT nextval('public.request_type_request_type_id_seq'::regclass);


--
-- TOC entry 4764 (class 2604 OID 16690)
-- Name: role id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role ALTER COLUMN id SET DEFAULT nextval('public.role_id_seq'::regclass);


--
-- TOC entry 4765 (class 2604 OID 16702)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 4766 (class 2604 OID 16711)
-- Name: working_time working_time_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.working_time ALTER COLUMN working_time_id SET DEFAULT nextval('public.working_time_working_time_id_seq'::regclass);


--
-- TOC entry 4768 (class 2606 OID 16604)
-- Name: attendance attendance_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.attendance
    ADD CONSTRAINT attendance_pkey PRIMARY KEY (attendance_id);


--
-- TOC entry 4770 (class 2606 OID 16613)
-- Name: contract contract_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contract
    ADD CONSTRAINT contract_pkey PRIMARY KEY (id);


--
-- TOC entry 4772 (class 2606 OID 16620)
-- Name: department department_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.department
    ADD CONSTRAINT department_pkey PRIMARY KEY (department_id);


--
-- TOC entry 4774 (class 2606 OID 16627)
-- Name: holiday holiday_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.holiday
    ADD CONSTRAINT holiday_pkey PRIMARY KEY (holiday_id);


--
-- TOC entry 4776 (class 2606 OID 16634)
-- Name: log_attendance_history log_attendance_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_attendance_history
    ADD CONSTRAINT log_attendance_history_pkey PRIMARY KEY (attendance_history_id);


--
-- TOC entry 4778 (class 2606 OID 16641)
-- Name: log_check_in_out log_check_in_out_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_check_in_out
    ADD CONSTRAINT log_check_in_out_pkey PRIMARY KEY (log_checkinout_id);


--
-- TOC entry 4780 (class 2606 OID 16648)
-- Name: log_in_late_out_early log_in_late_out_early_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_in_late_out_early
    ADD CONSTRAINT log_in_late_out_early_pkey PRIMARY KEY (log_inlateoutearly_id);


--
-- TOC entry 4782 (class 2606 OID 16657)
-- Name: position position_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."position"
    ADD CONSTRAINT position_pkey PRIMARY KEY (position_id);


--
-- TOC entry 4784 (class 2606 OID 16662)
-- Name: position_roles position_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.position_roles
    ADD CONSTRAINT position_roles_pkey PRIMARY KEY (position_id, role_id);


--
-- TOC entry 4788 (class 2606 OID 16678)
-- Name: request_category request_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request_category
    ADD CONSTRAINT request_category_pkey PRIMARY KEY (request_category_id);


--
-- TOC entry 4786 (class 2606 OID 16671)
-- Name: request request_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request
    ADD CONSTRAINT request_pkey PRIMARY KEY (request_id);


--
-- TOC entry 4790 (class 2606 OID 16685)
-- Name: request_type request_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request_type
    ADD CONSTRAINT request_type_pkey PRIMARY KEY (request_type_id);


--
-- TOC entry 4792 (class 2606 OID 16692)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- TOC entry 4794 (class 2606 OID 16697)
-- Name: sign sign_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sign
    ADD CONSTRAINT sign_pkey PRIMARY KEY (sign_id);


--
-- TOC entry 4796 (class 2606 OID 16715)
-- Name: users uk_6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- TOC entry 4798 (class 2606 OID 16706)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4800 (class 2606 OID 16713)
-- Name: working_time working_time_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.working_time
    ADD CONSTRAINT working_time_pkey PRIMARY KEY (working_time_id);


--
-- TOC entry 4807 (class 2606 OID 16747)
-- Name: log_check_in_out fk3348gbb1ck4uksbe3hj0cnf5d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_check_in_out
    ADD CONSTRAINT fk3348gbb1ck4uksbe3hj0cnf5d FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4813 (class 2606 OID 16777)
-- Name: request_type fk5hvutd3vg845f5iirsuk8wtc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request_type
    ADD CONSTRAINT fk5hvutd3vg845f5iirsuk8wtc FOREIGN KEY (request_category_id) REFERENCES public.request_category(request_category_id);


--
-- TOC entry 4814 (class 2606 OID 16782)
-- Name: users fk7vgyxapb64uaodvvpvpubeert; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk7vgyxapb64uaodvvpvpubeert FOREIGN KEY (department_id) REFERENCES public.department(department_id);


--
-- TOC entry 4804 (class 2606 OID 16732)
-- Name: log_attendance_history fk8aecqs13iob6i6ky6matw7jef; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_attendance_history
    ADD CONSTRAINT fk8aecqs13iob6i6ky6matw7jef FOREIGN KEY (attendance_id) REFERENCES public.attendance(attendance_id);


--
-- TOC entry 4815 (class 2606 OID 16787)
-- Name: users fkay81wkdumu7athhw45e1l1lln; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fkay81wkdumu7athhw45e1l1lln FOREIGN KEY (position_id) REFERENCES public."position"(position_id);


--
-- TOC entry 4811 (class 2606 OID 16772)
-- Name: request fkg03bldv86pfuboqfefx48p6u3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request
    ADD CONSTRAINT fkg03bldv86pfuboqfefx48p6u3 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4803 (class 2606 OID 16727)
-- Name: contract fki6rphdb5rpnqnrp5twyk83jao; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contract
    ADD CONSTRAINT fki6rphdb5rpnqnrp5twyk83jao FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4812 (class 2606 OID 16767)
-- Name: request fkibmr315gqv6g75nhasnydst5w; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.request
    ADD CONSTRAINT fkibmr315gqv6g75nhasnydst5w FOREIGN KEY (request_type_id) REFERENCES public.request_type(request_type_id);


--
-- TOC entry 4801 (class 2606 OID 16722)
-- Name: attendance fkjcaqd29v2qy723owsdah2t8vx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.attendance
    ADD CONSTRAINT fkjcaqd29v2qy723owsdah2t8vx FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4805 (class 2606 OID 16742)
-- Name: log_attendance_history fkjk3rw5hr0h5ggpn881u3gugh9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_attendance_history
    ADD CONSTRAINT fkjk3rw5hr0h5ggpn881u3gugh9 FOREIGN KEY (old_signs_id) REFERENCES public.sign(sign_id);


--
-- TOC entry 4802 (class 2606 OID 16717)
-- Name: attendance fkmrksvah1c131dhkb8g4wyx4hi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.attendance
    ADD CONSTRAINT fkmrksvah1c131dhkb8g4wyx4hi FOREIGN KEY (signs_id) REFERENCES public.sign(sign_id);


--
-- TOC entry 4809 (class 2606 OID 16757)
-- Name: position_roles fkns0g5lbet1viwpnyaffh8m51k; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.position_roles
    ADD CONSTRAINT fkns0g5lbet1viwpnyaffh8m51k FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- TOC entry 4808 (class 2606 OID 16752)
-- Name: log_in_late_out_early fkpvam1mwq724d82ntch7xntmg9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_in_late_out_early
    ADD CONSTRAINT fkpvam1mwq724d82ntch7xntmg9 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4810 (class 2606 OID 16762)
-- Name: position_roles fkpy5tejqwi1y43h4a0ei86v94c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.position_roles
    ADD CONSTRAINT fkpy5tejqwi1y43h4a0ei86v94c FOREIGN KEY (position_id) REFERENCES public."position"(position_id);


--
-- TOC entry 4806 (class 2606 OID 16737)
-- Name: log_attendance_history fktk3pxf2a97fqkticupoxu2ay3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_attendance_history
    ADD CONSTRAINT fktk3pxf2a97fqkticupoxu2ay3 FOREIGN KEY (new_signs_id) REFERENCES public.sign(sign_id);


-- Completed on 2023-11-06 21:54:34

--
-- PostgreSQL database dump complete
--
--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

-- Started on 2023-11-06 21:55:09

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
-- TOC entry 4952 (class 0 OID 16614)
-- Dependencies: 218
-- Data for Name: department; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.department VALUES (1, NULL, NULL, NULL, NULL, 'Phong phat trien phan mem');


--
-- TOC entry 4959 (class 0 OID 16650)
-- Dependencies: 225
-- Data for Name: position; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."position" VALUES (1, NULL, NULL, NULL, NULL, 'Trưởng phòng');


--
-- TOC entry 4969 (class 0 OID 16693)
-- Dependencies: 235
-- Data for Name: sign; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4971 (class 0 OID 16699)
-- Dependencies: 237
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users VALUES (1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'donghphe150112@fpt.edu.vn', NULL, 'Ho Phuong Dong', NULL, '$2a$12$eIIbe6MDqDzkYfk4U389ZeHShft56A1fBTwjiTjawvlG.BvtIKhuK', NULL, NULL, NULL, 'FPT_00018', NULL, 'dong', 1, 1);


--
-- TOC entry 4949 (class 0 OID 16597)
-- Dependencies: 215
-- Data for Name: attendance; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4951 (class 0 OID 16606)
-- Dependencies: 217
-- Data for Name: contract; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4954 (class 0 OID 16622)
-- Dependencies: 220
-- Data for Name: holiday; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4955 (class 0 OID 16628)
-- Dependencies: 221
-- Data for Name: log_attendance_history; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4956 (class 0 OID 16635)
-- Dependencies: 222
-- Data for Name: log_check_in_out; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4957 (class 0 OID 16642)
-- Dependencies: 223
-- Data for Name: log_in_late_out_early; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4968 (class 0 OID 16687)
-- Dependencies: 234
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.role VALUES (1, 'ROLE_MODERATOR');
INSERT INTO public.role VALUES (2, 'ROLE_ADMIN');
INSERT INTO public.role VALUES (3, 'ROLE_USER');


--
-- TOC entry 4960 (class 0 OID 16658)
-- Dependencies: 226
-- Data for Name: position_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.position_roles VALUES (1, 2);


--
-- TOC entry 4964 (class 0 OID 16673)
-- Dependencies: 230
-- Data for Name: request_category; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4966 (class 0 OID 16680)
-- Dependencies: 232
-- Data for Name: request_type; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4962 (class 0 OID 16664)
-- Dependencies: 228
-- Data for Name: request; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4973 (class 0 OID 16708)
-- Dependencies: 239
-- Data for Name: working_time; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4980 (class 0 OID 0)
-- Dependencies: 216
-- Name: contract_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.contract_id_seq', 1, false);


--
-- TOC entry 4981 (class 0 OID 0)
-- Dependencies: 240
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);


--
-- TOC entry 4982 (class 0 OID 0)
-- Dependencies: 219
-- Name: holiday_holiday_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.holiday_holiday_id_seq', 1, false);


--
-- TOC entry 4983 (class 0 OID 0)
-- Dependencies: 224
-- Name: position_position_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.position_position_id_seq', 1, true);


--
-- TOC entry 4984 (class 0 OID 0)
-- Dependencies: 229
-- Name: request_category_request_category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.request_category_request_category_id_seq', 1, false);


--
-- TOC entry 4985 (class 0 OID 0)
-- Dependencies: 227
-- Name: request_request_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.request_request_id_seq', 1, false);


--
-- TOC entry 4986 (class 0 OID 0)
-- Dependencies: 231
-- Name: request_type_request_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.request_type_request_type_id_seq', 1, false);


--
-- TOC entry 4987 (class 0 OID 0)
-- Dependencies: 233
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.role_id_seq', 3, true);


--
-- TOC entry 4988 (class 0 OID 0)
-- Dependencies: 236
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 1, true);


--
-- TOC entry 4989 (class 0 OID 0)
-- Dependencies: 238
-- Name: working_time_working_time_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.working_time_working_time_id_seq', 1, false);


-- Completed on 2023-11-06 21:55:09

--
-- PostgreSQL database dump complete
--


