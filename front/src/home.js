import React, { Component } from 'react';
import './App.css';
import smap from './pictrue/smap.png';
import {Link} from 'react-router-dom';
import axios from 'axios';
import qs from 'qs';
import moment from 'moment';
import {DatePicker} from 'antd';
import 'antd/dist/antd.css';
import userpic from './pictrue/userpic.jpg'

class Home extends Component {
    constructor(props){
        super(props);
        this.state= {
            user: "粥西奥",
            camera: "camera_0",
            time: moment(new Date(), "YYYY-MM-DD"),
            hourlist: [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23],
            hour:0,
            hidden:true,
            personlist:["person0"],
            personhidden:true,
            person:"person0",
            imgname:"znz.jpg",
            buff:"znz.jpg",
            buthidden:true,
        };

        this.camera=this.camera.bind(this);
        this.setDate=this.setDate.bind(this);
        this.setHour=this.setHour.bind(this);
        this.sort=this.sort.bind(this);
        this.showTime=this.showTime.bind(this);
        this.showVideo=this.showVideo.bind(this);
        this.setPerson=this.setPerson.bind(this);
        this.selectPerson=this.selectPerson.bind(this);
        this.showimg=this.showimg.bind(this);
    }
    /****************************************************/
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
    /**********************************************************/
    setDate=(value)=>{
        this.setState({
            time:value
        })
    };
    /************************************************************/
    setHour(e){
        this.setState({
            hour:e.target.value
        })
    };
    /********************************************************/
    setPerson(e){
        this.setState({
            person:e.target.value
        })
    }
    /************************************************************/
    selectPerson=()=>{
        axios.post("/controllerblock/selectPerson",qs.stringify({
            person:this.state.person
        }))
            .then((response)=>{
                alert(response.data.msg);
                console.log(response);
            })
            .catch((error)=>{
                console.log(error)
            });
        window.location.reload();
    };
    /***************************************************/
    sort=()=>{
        this.setState({loading:false});
        axios.post("/controllerblock/sortVideoByTime",qs.stringify({
            camera:this.state.camera,
            time:this.state.time.format("YYYY-MM-DD").toString(),
            hour:this.state.hour
        }))
            .then((response)=>{
                alert(response.data.msg);
                if(response.data.msg==="SUCCESS"){
                    this.setState({
                        personlist:response.data.personlist,
                        buff:response.data.imgname,
                        buthidden:false,
                        loading:true
                    })
                }
                console.log(response);

            })
            .catch((error)=>{
                alert(error);
                console.log(error);
            })
    };
    /*********************************************/
    showTime(){
     this.setState({
         hidden:false
     })
    }

    /*******************************/
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
    /**************************************/
    showimg(){
        this.setState({
            personhidden:false,
            imgname:this.state.buff
        })
    }

    /*****************************************/
    render() {
        return (
            <div className="Home">
                <header className="App-header">
                    <Link className={"homeLink"} to={"/admin"}>地图管理</Link><Link className={"homeLink"} to={"/login"}>返回登录</Link>
                    <label className={"user"}>{this.state.user}</label>
                    <img src={userpic} alt={"用户头像"} className={"userpic"}/>
                    <h1 className="App-title">首页-控制面板</h1>
                </header>

                <div className={"control"}>
                    <div className="videobox" >
                        <h1>选定摄像头:<strong>{this.state.camera}</strong></h1>
                        <button id={"now"} onClick={this.showVideo} className="blue">实时视频</button>
                        <button onClick={this.showTime} id={"his"} className="blue">历史视频</button>
                        <button className="blue" onClick={this.showimg} hidden={this.state.buthidden}>展示截图</button>
                    </div>

                    <div hidden={this.state.hidden} className={"time"}>
                        <DatePicker defaultValue={this.state.time} onChange={this.setDate}/>
                        <label >&nbsp;&nbsp;小时：</label>
                        <select onChange={this.setHour} >
                        {
                            this.state.hourlist.map(function (item) {
                                return(<option>{item}</option>);
                            })
                        }
                        </select>
                        <button onClick={this.sort} className="blue">搜索</button>

                        <div hidden={this.state.personhidden}>
                            <img src={require("./pictrue/"+this.state.imgname)} alt={"截图无法显示"} className={"cut"}/><br/>
                            <select onChange={this.setPerson}>
                                {this.state.personlist.map(function (person) {
                                return(<option id={person}>{person}</option>)
                                })}
                            </select>
                            <button className="blue" onClick={this.selectPerson}>选定目标</button>
                        </div>

                    </div>
                </div>

                <div className={"ssmap"}>
                    <h1>建筑平面图</h1>
                    <img className={"smap"} src={smap} border="0" useMap={'#mapmap'} alt={"map"}/>
                </div>

                <map id={'mapmap'} name={'mapmap'}>
                    <area id="camera_0"  shape="rect" coords="270,90,360,160" onClick={this.camera} alt={"camera1"} />
                    <area id="camera_1"  shape="rect" coords="630,205,700,275" onClick={this.camera} alt={"camera2"}/>
                    <area id="camera_2"  shape="rect" coords="270,300,340,370" onClick={this.camera} alt={"camera3"}/>
                </map>

            </div>
        );
    }
}

export default Home;