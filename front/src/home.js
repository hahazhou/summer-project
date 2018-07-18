import React, { Component } from 'react';
import userpic from './pictrue/userpic.jpg';
import './App.css';
import smap from './pictrue/smap.png';
import {Link} from 'react-router-dom';
import axios from 'axios';
import qs from 'qs';
import moment from 'moment';
import {DatePicker} from 'antd';


class Home extends Component {
    constructor(props){
        super(props);
        this.state= {
            user: "粥西奥",
            camera: "camera_1",
            time: moment(new Date(), "YYYY-MM-DD"),
            hourlist: [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23],
            hour:0,
            hidden:true
        };

        this.camera=this.camera.bind(this);
        this.setDate=this.setDate.bind(this);
        this.setHour=this.setHour.bind(this);
        this.sort=this.sort.bind(this);
        this.showTime=this.showTime.bind(this);
        this.showVideo=this.showVideo.bind(this);
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
                if(response.data.msg==="切换成功"){
                    this.setState({
                        camera:response.data.camera
                    })
                }
                alert(response.data.msg+this.state.camera);
            })
            .catch((error)=>{
                console.log(error);
                alert(error);
            })
    };

    setDate(e){
        this.setState({
            time:e.target.value
        })
    }

    setHour(e){
        this.setState({
            hour:e.target.value
        })
    }

    sort=()=>{
        axios.post("/controllerblock/sortVideoByTime",qs.stringify({
            camera:this.state.camera,
            time:this.state.time,
            hour:this.state.hour
        }))
            .then((response)=>{
                alert(response.data.msg);
                console.log(response);
            })
            .catch((error)=>{
                alert(error);
                console.log(error);
            })
    };

    showTime(){
     this.setState({
         hidden:false
     })
    }

    showVideo=()=>{
        this.setState({
            hidden:true
        });
        axios.post("/controllerblock/showVideo",qs.stringify({
            camera:this.state.camera
        }))
            .then((response)=>{
                alert(response.data.msg);
                console.log(response);
            })
            .catch((error)=>{
                alert(error);
                console.log(error);
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
                <div className="videobox" >
                    <h1>选定摄像头:<strong>{this.state.camera}</strong></h1>
                    <button id={"now"} onClick={this.showVideo}>实时视频</button><label>&nbsp;&nbsp;</label><button onClick={this.showTime} id={"his"}>历史视频</button>

                </div>
                <div hidden={this.state.hidden}>
                    <DatePicker defaultValue={this.state.time} className={"datapicker"} onChangeMonthYear={this.setDate}/>
                    <label className={"hour"}>小时：</label>
                    <select className={"hour"} onChange={this.setHour} >
                    {
                        this.state.hourlist.map(function (item) {
                            return(<option>{item}</option>);
                        })
                    }
                    </select>
                    <button onClick={this.sort}>搜索</button>
                    <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
                </div>
                <h1>建筑平面图</h1>
                <p className={"ssmap"}>
                <img className="smap" src={smap} border="0" useMap={'#mapmap'} alt={"map"}/>
                </p>
                <map id={'mapmap'} name={'mapmap'}>
                    <area id="camera_0"  shape="rect" coords="270,90,360,160" onClick={this.camera} alt={"camera1"}/>
                    <area id="camera_1"  shape="rect" coords="630,205,700,275" onClick={this.camera} alt={"camera2"}/>
                    <area id="camera_2"  shape="rect" coords="270,300,340,370" onClick={this.camera} alt={"camera3"}/>
                </map>
            </div>
        );
    }
}

export default Home;