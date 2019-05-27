const serviceError = function(err) {
  console.log(err)
  let msg = '请求出错!'
  if (err.data) {
    msg = err.data.message
  }
  this.$message.error(msg)
}

const getVideoTime = (duration) => {
  if (duration) {
    let secondTotal = Math.round(duration)

    let hour = Math.floor(secondTotal / 3600)
    let minute = Math.floor((secondTotal - hour * 3600) / 60)

    let second = secondTotal - hour * 3600 - minute * 60

    if (minute < 10) {
      minute = '0' + minute
    }
    if (second < 10) {
      second = '0' + second
    }
    return hour === 0 ? minute + ':' + second : hour + ':' + minute + ':' + second
  }
  return 0
}

const getFileSize = (size) => {
  let _size = size / 1024 / 1024
  return Math.round(_size * 100) / 100 + 'M'
}

const getFormatDate = function(date) {
  let year = date.getFullYear()
  let month = date.getMonth() + 1
  let dateFormat = date.getDate()

  let hour = date.getHours()
  hour = hour < 10 ? ('0' + hour) : hour

  let minute = date.getMinutes()
  minute = minute < 10 ? ('0' + minute) : minute

  let second = date.getSeconds()
  second = second < 10 ? ('0' + second) : second

  return year + '-' + month + '-' + dateFormat + ' ' + hour + ':' + minute + ':' + second
}

export default {
  methods: {
    serviceError,
    getVideoTime,
    getFileSize,
    getFormatDate,
  }
}