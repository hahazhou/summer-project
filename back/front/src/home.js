import React, { Component } from 'react';
import userpic from './pictrue/userpic.jpg';
import './App.css';
import myVideo1 from 'D:/网盘资源/心理07.mp4';
import myVideo2 from 'D:/网盘资源/心理08.mp4';
import myVideo3 from 'D:/网盘资源/心理09.mp4'
import smap from './pictrue/smap.png';
import {Link} from 'react-router-dom';
import axios from 'axios';
import qs from 'qs';


class Home extends Component {
    constructor(props){
        super(props);
        this.state={
            user:"粥西奥",
            camera:"camera_1",
            video:myVideo1
        };
        this.camera=this.camera.bind(this);
    }

    camera=(e)=>{
        axios.post("/controllerblock/changeCamera",qs.stringify(
            {
                user:this.state.user,
                camera:e.target.id
            }
        ))
            .then((response)=>{
                console.log(response);
                if(response.data.msg=="切换成功"){
                    this.setState({
                        camera:response.data.camera,
                        video:response.data.video
                    })
                }
                alert(response.data.msg+this.state.camera);
            })
            .catch((error)=>{
                console.log(error);
                alert(error);
            })
    };


    render() {
        return (
            <div className="Home">
                <header className="App-header">
                    <h1 className="App-title">首页-控制面板</h1>
                    <img src={userpic} className="App-logo"  alt={"userpicture"}/><br/>
                    <label>{this.state.user}</label><label>&nbsp;&nbsp;</label>
                    <Link className={"sLink"} to={"/login"}>返回登录</Link>&nbsp;&nbsp;<Link className={"sLink"} to={"/admin"}>地图管理</Link>
                </header>
                <h1>建筑平面图</h1>
                <p className={"ssmap"}>
                <img className="smap" src={smap} border="0" useMap={'#mapmap'} alt={"map"}/>
                </p>
                <map id={'mapmap'} name={'mapmap'}>
                    <area id="camera_1"  shape="rect" coords="270,90,360,160" onClick={this.camera} alt={"camera1"}/>
                    <area id="camera_2"  shape="rect" coords="630,205,700,275" onClick={this.camera} alt={"camera2"}/>
                    <area id="camera_3"  shape="rect" coords="270,300,340,370" onClick={this.camera} alt={"camera3"}/>
                </map>
                <div className="videobox">
                    <h1>摄像头<strong>{this.state.camera}</strong>的视频</h1>
                    <p className={"vvideo"}>
                    <video className="video" id="video">
                        <source src={this.state.video} type="video/mp4" />
                    </video>
                        {this.state.video}
                    </p>
                </div>
            </div>
        );
    }
}

export default Home;