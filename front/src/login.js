import React, { Component } from 'react';
import './App.css'
import {Link} from 'react-router-dom'

class Login extends Component{
    render(){
        return(
            <div>
                <header className="App-header">
                    <h1 className="App-title">登录界面</h1><br/>
                    <Link to={"/home"} className={"sLink"}>首页</Link><p>&nbsp;&nbsp;</p>
                </header>
                <p className={"center"}>
                <form>
                    <label>用户名:</label>
                    <input type={"text"}/><br/><br/>
                    <label>密&nbsp;码:</label>
                    <input type={"password"}/><br/><br/>
                    <select>
                        <option>普通用户</option>
                        <option>管理员</option>
                    </select><p>&nbsp;</p>
                    <input type={"submit"}/>
                </form>
                </p>
            </div>
        )
    }
}

export default Login;