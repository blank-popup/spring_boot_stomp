<template>
  <div id='app'>
    Channel:
    <input
      v-model='channel'
      type='text'
    >
    <button @click='connect'>Connect</button>
    Username:
    <input
      v-model='username'
      type='text'
    >
    Message: <input
      v-model='message'
      type='text'
      @keyup='sendMessage'
    >
    <div
      v-for='(item, idx) in recordings'
      :key='idx'
    >
      <p>{{ item.channel }} : {{ item.username }} : {{ item.message }}</p>
    </div>
  </div>
</template>

<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  name: 'App',
  data() {
    return {
      channel: '',
      username: '',
      message: '',
      recordings: []
    }
  },
  created() {
  },
  methods: {
    sendMessage (e) {
      if(e.keyCode === 13 && this.username !== '' && this.message !== ''){
        this.send()
        this.message = ''
      }
    },
    send() {
      console.log('Send message:' + this.message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          channel: this.channel,
          username: this.username,
          message: this.message
        };
        this.stompClient.send('/publish', JSON.stringify(msg), {});
      }
    },
    connect() {
      const serverURL = 'http://localhost:8080/websocket'
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`Try to connect: ${serverURL}`)
      this.stompClient.connect(
        {},
        frame => {
          this.connected = true;
          console.log('Success to connect:', frame);
          this.stompClient.subscribe('/subcribe/' + this.channel, res => {
            console.log('Subscribing message:', res.body);
            this.recordings.push(JSON.parse(res.body))
          });
        },
        error => {
          console.log('Fail to connect', error);
          this.connected = false;
        }
      );
    }
  }
}
</script>
