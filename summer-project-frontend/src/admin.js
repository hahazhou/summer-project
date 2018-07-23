import React, {Component} from 'react';
import './App.css';
import znz from './pictrue/znz.jpg'
import {Link} from 'react-router-dom'
import userpic from './pictrue/userpic.jpg';

class Admin extends Component {
    constructor(props) {
        super(props);
        this.state = {
            img: null,
            user: "喵喵喵",
            text: "",
            info: "",
            cwidth: 600,
            cheight: 400
        };

        this.postpicture = this.postpicture.bind(this);
        this.cnvs_getCoordinates = this.cnvs_getCoordinates.bind(this);
    }

    postpicture(e) {
        let filePath = e.target.value;         //获取到input的value，里面是文件的路径
        let fileFormat = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
        // 检查是否是图片
        if (!fileFormat.match(/.png|.jpg|.jpeg/)) {
            alert('上传错误,文件格式必须为：png/jpg/jpeg');
            return;
        }
        this.setState({
            img: window.URL.createObjectURL(e.target.files[0]),
            info: filePath
        });

        let c = this.canvas;
        let cxt = c.getContext("2d");
        let img = new Image();
        img.src = this.state.info;
        cxt.drawImage(img, 0, 0);

    }

    cnvs_getCoordinates(e) {
        let x = e.clientX;
        let y = e.clientY;
        this.setState({text: "Coordinates: (" + x + "," + y + ")"});
    }


    render() {
        return (
            <div className="Admin">
                <header className="App-header">
                    <h1 className="App-title">管理员界面</h1>
                    <img src={userpic} className="App-logo" alt={"userpic"}/><br/>
                    <label><strong>{this.state.user}&nbsp;&nbsp;</strong></label>
                    <Link className={"sLink"} to={"/home"}>返回首页</Link>&nbsp;&nbsp;<Link className={"sLink"}
                                                                                        to={"/login"}>退出登录</Link>
                </header>
                < p className={"center"}>
                    <canvas ref={ref => this.canvas = ref} width={this.state.cwidth + "px"}
                            height={this.state.cheight + "px"} className={"mycanvas"}
                            onMouseMove={this.cnvs_getCoordinates}>
                        <p>画面无法显示</p>
                    </canvas>
                    <form className="container" encType="multipart/form-data" method="post" id='formBox' name="formBox">
                        <input type="file" onChange={this.postpicture}/>
                        <input type="submit"/>
                    </form>

                    <p>{this.state.text}</p>
                    <p>{this.state.info}</p>

                    <br/>
                    <img src={this.state.img} alt={"图片无法显示"}/>
                    <br/>
                    <h1>摄像头参数设置</h1>
                    <from>
                        <img className={"znz"} src={znz} alt={"指南针方向"}/><br/>
                        <label>摄像头位置:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <label>x:</label><input/><br/>
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            &nbsp;&nbsp;&nbsp;y:</label><input/><br/>
                        <label>摄像头方向&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：</label><input/><br/>
                        <label>摄像头仰角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：</label><input/><br/>
                        <label>摄像头监控半径：</label><input/><br/>
                        <label>比例尺&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：</label><input/><label>:1</label><br/>
                        <input type={"submit"}/>
                    </from>
                </p>


            </div>
        );
    }
}

export default Admin;