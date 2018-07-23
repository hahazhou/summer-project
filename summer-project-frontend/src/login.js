import React, {Component} from 'react';
import './App.css'
import {Link, Redirect} from 'react-router-dom'
import axios from 'axios'
import qs from 'qs'


class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            redirect: false
        };

        this.setUserName = this.setUserName.bind(this);
        this.setPassword = this.setPassword.bind(this);
        this.tologin = this.tologin.bind(this);
    }

    tologin = () => {
        axios.post("/login/tologin", qs.stringify({
            username: this.state.username,
            password: this.state.password
        }))
            .then((response) => {
                    console.log(response);
                    alert(response.data.msg);
                    if (response.data.msg === "登录成功") {
                        this.setState({
                            redirect: true
                        })
                    }
                }
            )
            .catch((error) => {
                console.log(error);
                alert(error);
            })
    };

    setUserName(e) {
        this.setState({
            username: e.target.value
        });

    };


    setPassword(e) {
        this.setState({
            password: e.target.value
        })
    }

    render() {
        if (!this.state.redirect) {
            return (
                <div>
                    <header className="App-header">
                        <h1 className="App-title">登录界面</h1><br/>
                        <Link to={"/home"} className={"sLink"}>首页</Link><p>&nbsp;&nbsp;</p>
                    </header>
                    <p className={"center"}>

                        <label>用户名:</label>
                        <input onChange={this.setUserName} id="username" type={"text"}/><br/><br/>
                        <p>{this.state.username}</p>
                        <label>密&nbsp;码:</label>
                        <input id={"password"} type={"password"} onChange={this.setPassword}/><br/><br/>
                        <button onClick={this.tologin}>登录</button>

                    </p>
                </div>
            )
        } else {
            return <Redirect push to={"/home"}/>
        }
    }
}

export default Login;