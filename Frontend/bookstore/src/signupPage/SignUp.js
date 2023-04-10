import React from "react";
import { useNavigate } from "react-router-dom";
import "./signup.css";
import {environment} from '../Environment';

export const ValidateEmail = (mail = "") =>
  /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(mail) || mail === "";
export const Validatepassword = (password = "") =>
  /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(password) || password === "";
export const ValidatePhonenumber = (number = "") =>
  /^01[0125][0-9]{8}$/gm.test(number) || number === "";
export const VaidateAdress = (address = "") =>
  /^[A-Za-z0-9-#-,- ]*$/.test(address) || address === "";

function SignUp() {
  let nav = useNavigate();
  const [Error, setError] = React.useState(false);
  //userName, Phone, FName, LName, email, Password, promoted, Address, PromoteMN
  let [info, setInfo] = React.useState({
    fname: "",
    lname: "",
    email: "",
    address: "",
    phone: "",
    password: "",
    userName:""
  });
  const [User, setuser] = React.useState("customer");
  let [errors, setErrors] = React.useState({
    mail: false,
    password: false,
    phone: false,
    address: false
  });

  let handleChange = (e) => {
    setInfo((prev) => {
      return { ...prev, [e.target.name]: e.target.value };
    });
    if (e.target.name === "email") {
      let flag = ValidateEmail(e.target.value);
      setErrors((prev) => {
        return { ...prev, mail: !flag };
      });
      console.log(errors.mail);
    } else if (e.target.name === "phone") {
      let flag = ValidatePhonenumber(e.target.value);
      setErrors((prev) => {
        return { ...prev, phone: !flag };
      });
    } else if (e.target.name === "password") {
      let flag = Validatepassword(e.target.value);
      setErrors((prev) => {
        return { ...prev, password: !flag };
      });
    } else if (e.target.name === "address") {
      let flag = VaidateAdress(e.target.value);
      setErrors((prev) => {
        return { ...prev, address: !flag };
      });
    }
  };
  let handleSubmit = async (e) => {
    e.preventDefault();
    if (
      errors.mail ||
      errors.password ||
      errors.phone ||
      errors.address
    ) {
      return;
    }
    let route = User==="customer"? "up": "upManager";
    let result = await fetch(`${environment.Host}/sign/${route}`, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify(info),
    });
    let message = await result.json();
    console.log(message);
    if (message.state === "accepted") {
      nav("/");
    } else {
      setError(() => { return true; });
    }
  };

  return (
    <div className="sign-up">
      <div className="form-container">
        <h1 className="sign-up-header">Sign Up</h1>
        <form onSubmit={handleSubmit}>
          <div className="contain">
            <input
              type="text"
              placeholder="first Name"
              name="fname"
              required
              value={info.fname}
              onChange={handleChange}
            />
          </div>
          <div className="contain">
            <input
              type="text"
              placeholder="Last Name"
              name="lname"
              required
              value={info.lname}
              onChange={handleChange}
            />
          </div>
          <div className="contain">
            <input
              className={errors.mail ? "error" : " "}
              type="mail"
              placeholder="Email"
              name="email"
              required
              value={info.email}
              onChange={handleChange}
            />
            <div className={errors.mail ? "error-message" : "hide"}>
              Invalid Email address: please enter a Valid one.
            </div>
          </div>
          <div className="contain">
            <input
              className={errors.password ? "error" : " "}
              type="password"
              placeholder="Password: at least 8 characters"
              name="password"
              required
              value={info.password}
              onChange={handleChange}
            />
            <div className={errors.password ? "error-message" : "hide"}>
              Invalid password: password should consist of at least 8 characters
              with at least 1 digit and 1 alphabetic character (all in small
              case).
            </div>
          </div>
          <div className="contain">
            <input
              className={errors.phone ? "error" : " "}
              type="text"
              placeholder="Phone Number"
              name="phone"
              required
              value={info.phone}
              onChange={handleChange}
            />
            <div className={errors.phone ? "error-message" : "hide"}>
              Invalid phone number, please enter a valid 11-digit phone number.
            </div>
          </div>
          <div className="contain">
            <input
              type="text"
              placeholder="User name"
              name="userName"
              required
              value={info.userName}
              onChange={handleChange}
            />
          </div>
          <div className="contain">
            <input
              className={errors.address ? "error" : " "}
              type="text"
              placeholder="Address"
              name="address"
              required
              value={info.address}
              onChange={handleChange}
            />
            <div className={errors.address ? "error-message" : "hide"}>
              Invalid address: address should contain only alphabets, digits and
              the # symbol.
            </div>
          </div>
          <div className='check-boxs'>
            <div >
              <input className="checkbox" type="checkbox" checked={User === "customer"} onChange={() => setuser("customer")} /> Customer</div>
            <div>
              <input className="checkbox" type="checkbox" checked={User === "manager"} onChange={() => setuser("manager")} /> Manager</div>
          </div>
          <input
            className="btn-submit"
            type="submit"
            value="Sign Up"
            name="Sign Up"
          />
        </form>
      </div>
      {Error && <div className='ErrorPOP'>
        <div className='PopUP'>
          <p>This Email Is in use !!</p>
          <button onClick={() => { setError(() => { return false; }) }}>OK</button>
        </div>
      </div>}
    </div>
  );
}

export default SignUp;
