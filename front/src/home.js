import React, { Component } from 'react';
import userpic from './pictrue/userpic.jpg';
import './App.css';
import myVideo1 from 'D:/网盘资源/心理07.mp4';
import myVideo2 from 'D:/网盘资源/心理08.mp4';
import myVideo3 from 'D:/网盘资源/心理09.mp4'
import smap from './pictrue/smap.png';
import {Link} from 'react-router-dom'


class Home extends Component {
    constructor(props){
        super(props);
        this.state={
            user:"喵喵喵",
            camera:"camera_1",
            video:myVideo1
        }
        this.camera1=this.camera1.bind(this);
        this.camera2=this.camera2.bind(this);
        this.camera3=this.camera3.bind(this);
    }

    camera1(){
        this.setState({
            camera:"camera_1",
            video:myVideo1
        })
        alert("成功切换到摄像头1");
    }

    camera2(){
        this.setState({
            camera:"camera_2",
            video:myVideo2
        })
        alert("成功切换到摄像头2");
    }

    camera3(){
        this.setState({
            camera:"camera_3",
            video:myVideo3
        })
        alert("成功切换到摄像头3");
    }

    render() {
        return (
            <div className="Home">
                <header className="App-header">
                    <h1 className="App-title">首页-控制面板</h1>
                    <img src={userpic} className="App-logo" /><br/>
                    <label><strong>{this.state.user}&nbsp;&nbsp;</strong></label>
                    <Link className={"sLink"} to={"/login"}>返回登录</Link>
                </header>
                <h1>建筑平面图</h1>
                <p className={"ssmap"}>
                <img className="smap" src={smap} border="0" useMap={'#mapmap'}/>
                </p>
                <map id={'mapmap'} name={'mapmap'}>
                    <area shape="rect" coords="270,90,360,160" onClick={this.camera1}/>
                    <area shape="rect" coords="630,205,700,275" onClick={this.camera2}/>
                    <area shape="rect" coords="270,300,340,370" onClick={this.camera3}/>
                </map>
                <div className="videobox">
                    <h1>摄像头<strong>{this.state.camera}</strong>的视频</h1>
                    <p className={"vvideo"}>
                    <video className="video" id="video">
                        <source src={this.state.video} type="video/mp4" />
                    </video>
                    </p>
                </div>
            </div>
        );
    }
}

export default Home;