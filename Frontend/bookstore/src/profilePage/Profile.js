import React from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "./profile.css";
import { environment } from "../Environment";

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
  const location = useLocation();
  let [info, setInfo] = React.useState({});
  const [view, setview] = React.useState(true);
  let route = location.state.type;
  React.useEffect(() => {
    async function getdata() {
      let result = await fetch(
        `${environment.Host}/${route}/get/${location.state.userName}`,
        {
          method: "GET",
          headers: {
            "Content-type": "application/json",
          },
        }
      );
      let res = await result.json();
      setInfo(res);
    }
    getdata();
  }, []);
  /*
    fname: "",
    lname: "",
    email: "",
    address: "",
    phone: "",
    password: "",
    userName: "",
*/
  let [errors, setErrors] = React.useState({
    mail: false,
    password: false,
    phone: false,
    address: false,
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
    if (errors.mail || errors.password || errors.phone || errors.address) {
      return;
    }
    let route =
      location.state.type === "manager"
        ? "manager/editManager"
        : "customer/editCustomer";
    let result = await fetch(`${environment.Host}/${route}`, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify(info),
    });
    let message = await result.json();
    console.log(message);
    if (message.state === "accepted") {
      nav("/homePage");
    } else {
      setError(() => {
        return true;
      });
    }
  };

  function EnableEdit() {
    setview((old) => !old);
  }
  return (
    <div className="sign-up">
      <d iv className="form-container">
        <h1 className="sign-up-header">Profile</h1>
        <form onSubmit={handleSubmit}>
          <div className="contain">
            <input
              disabled={view}
              type="text"
              placeholder={info.name}
              name="name"
              required
              value={info.name}
              onChange={handleChange}
            />
          </div>
          <div className="contain">
            <input
              disabled={view}
              className={errors.mail ? "error" : " "}
              type="mail"
              placeholder={info.email}
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
              disabled={view}
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
              placeholder={info.phone}
              name="phone"
              required
              value={info.phone}
              onChange={handleChange}
              disabled={view}
            />
            <div className={errors.phone ? "error-message" : "hide"}>
              Invalid phone number, please enter a valid 11-digit phone number.
            </div>
          </div>
          <div className="contain">
            <input
              type="text"
              placeholder={info.username}
              name="username"
              required
              value={info.username}
              onChange={handleChange}
              disabled={view}
            />
          </div>
          <div className="contain">
            <input
              className={errors.address ? "error" : " "}
              type="text"
              placeholder={info.address}
              name="address"
              required
              value={info.address}
              onChange={handleChange}
              disabled={view}
            />
            <div className={errors.address ? "error-message" : "hide"}>
              Invalid address: address should contain only alphabets, digits and
              the # symbol.
            </div>
          </div>
          {view && (
            <input
              className="btn-submit"
              type="button"
              value="Edit"
              name="Edit"
              onClick={EnableEdit}
            />
          )}
          {!view && (
            <input
              className="btn-submit"
              type="submit"
              value="Save"
              name="Sign Up"
            />
          )}
        </form>
      </d>
      {Error && (
        <div className="ErrorPOP">
          <div className="PopUP">
            <p>This Email Is in use !!</p>

            <button
              onClick={() => {
                setError(() => {
                  return false;
                });
              }}
            >
              OK
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

export default SignUp;
