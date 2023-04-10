import React from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "./Payment";
import { environment } from "../Environment";

export const ValidateCardNumber = (cardNumber = "") =>
  /^(\d{16})$/i.test(cardNumber) || cardNumber === "";
export const ValidateExpireMonth = (expireMonth = "") =>
  (/^(\d{1}|\d{2})$/.test(expireMonth) &&
    parseInt(expireMonth) <= 12 &&
    parseInt(expireMonth) > 0) ||
  expireMonth === "";
export const ValidateExpireYear = (expireYear = "") =>
  (/^(\d{2})$/.test(expireYear) && parseInt(expireYear) > 22) ||
  expireYear === "";
export const VaidateCvv = (Cvv = "") =>
  /^(\d{3})$/.test(Cvv) || ValidateExpireYear === "";

function SignUp() {
  let nav = useNavigate();
  const location = useLocation();
  const [Error, setError] = React.useState(false);
  let [info, setInfo] = React.useState({
    name: "",
    cardNumber: "",
    expireMonth: "",
    expireYear: "",
    Cvv: "",
  });
  let [errors, setErrors] = React.useState({
    cardNumber: false,
    expireMonth: false,
    expireYear: false,
    Cvv: false,
  });

  let handleChange = (e) => {
    setInfo((prev) => {
      return { ...prev, [e.target.name]: e.target.value };
    });
    if (e.target.name === "cardNumber") {
      let flag = ValidateCardNumber(e.target.value);
      console.log(flag);
      setErrors((prev) => {
        return { ...prev, cardNumber: !flag };
      });
    } else if (e.target.name === "expireMonth") {
      let flag = ValidateExpireMonth(e.target.value);
      setErrors((prev) => {
        return { ...prev, expireMonth: !flag };
      });
    } else if (e.target.name === "expireYear") {
      let flag = ValidateExpireYear(e.target.value);
      setErrors((prev) => {
        return { ...prev, expireYear: !flag };
      });
    } else if (e.target.name === "Cvv") {
      let flag = VaidateCvv(e.target.value);
      setErrors((prev) => {
        return { ...prev, Cvv: !flag };
      });
    }
  };

  let handleSubmit = async (e) => {
    e.preventDefault();
    if (
      errors.cardNumber ||
      errors.expireMonth ||
      errors.expireYear ||
      errors.Cvv
    ) {
      return;
    }
    // console.log(info);
    let result = await fetch(`${environment.Host}/customer/checkout`, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify({
        userName: location.state.userName, //cardID, userName
        cartID: location.state.cartID,
      }),
    });
    nav("/homePage");
  };

  return (
    <div className="sign-up">
      <div className="form-container">
        <h1 className="sign-up-header">Payment</h1>
        <form onSubmit={handleSubmit}>
          <div className="contain">
            <input
              type="text"
              placeholder="Name"
              name="name"
              required
              value={info.name}
              onChange={handleChange}
            />
          </div>
          <div className="contain">
            <input
              className={errors.cardNumber ? "error" : " "}
              type="numbers"
              placeholder="Card Number"
              name="cardNumber"
              required
              value={info.cardNumber}
              onChange={handleChange}
            />
            <div className={errors.cardNumber ? "error-message" : "hide"}>
              Invalid Card Number : please enter a Valid one.
            </div>
          </div>
          <div className="contain">
            <input
              className={errors.expireMonth ? "error" : " "}
              type="number"
              placeholder="expireMonth"
              name="expireMonth"
              required
              value={info.expireMonth}
              onChange={handleChange}
            />
            <div className={errors.expireMonth ? "error-message" : "hide"}>
              Expire Month not valid, please enter a valid month from 1 to 12 .
            </div>
          </div>
          <div className="contain">
            <input
              className={errors.expireYear ? "error" : " "}
              type="number"
              placeholder="Expire Year"
              name="expireYear"
              required
              value={info.expireYear}
              onChange={handleChange}
            />
            <div className={errors.expireYear ? "error-message" : "hide"}>
              Invalid Expire Year, please enter a valid Year 23 or higher.
            </div>
          </div>
          <div className="contain">
            <input
              className={errors.Cvv ? "error" : " "}
              type="text"
              placeholder="Cvv"
              name="Cvv"
              required
              value={info.Cvv}
              onChange={handleChange}
            />
            <div className={errors.Cvv ? "error-message" : "hide"}>
              Invalid CVV: Cvv is 3 digits on the back of card.
            </div>
          </div>
          <input
            className="btn-submit"
            type="submit"
            value="Sign Up"
            name="Sign Up"
          />
        </form>
      </div>
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
