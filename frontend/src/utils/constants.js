import shortid from "shortid";

export const NAV_ITEMS = [
  {
    _id: shortid.generate(),
    link: "/stations",
    text: "역 관리",
  },
  {
    _id: shortid.generate(),
    link: "/lines",
    text: "노선 관리",
  },
  {
    _id: shortid.generate(),
    link: "/sections",
    text: "구간 관리",
  },
];

export const SNACKBAR_MESSAGES = {
  COMMON: {
    SUCCESS: "😀 성공적으로 변경하였습니다.",
    FAIL: "😰 오류가 발생했습니다.",
  },
  STATION: {
    CREATE: {
      SUCCESS: "😀 새로운 역을 생성했습니다.",
      FAIL: "😰 새로운 역을 추가하는 도중에 오류가 발생했습니다.",
    },
    DELETE: {
      SUCCESS: "😀 역을 성공적으로 삭제했습니다.",
      FAIL: "😰 역을 삭제하는 도중에 오류가 발생했습니다.",
    },
  },
  LINE: {
    CREATE: {
      SUCCESS: "😀 새로운 노선을 생성했습니다.",
      FAIL: "😰 새로운 노선을 추가하는 도중에 오류가 발생했습니다.",
    },
    UPDATE: {
      SUCCESS: "😀 노선을 성공적으로 수정했습니다.",
      FAIL: "😰 노선을 수정하는 도중에 오류가 발생했습니다.",
    },
    DELETE: {
      SUCCESS: "😀 노선을 성공적으로 삭제했습니다.",
      FAIL: "😰 노선을 삭제하는 도중에 오류가 발생했습니다.",
    },
  },
};

export const PATH_TYPE = {
  DISTANCE: "DISTANCE",
  DURATION: "DURATION",
  ARRIVAL_TIME: "ARRIVAL_TIME",
};

export const LINE_COLORS = [
  "grey lighten-5",
  "grey lighten-4",
  "grey lighten-3",
  "grey lighten-2",
  "grey lighten-1",
  "grey darken-1",
  "grey darken-2",
  "grey darken-3",
  "grey darken-4",

  "red lighten-5",
  "red lighten-4",
  "red lighten-3",
  "red lighten-2",
  "red lighten-1",
  "red darken-1",
  "red darken-2",
  "red darken-3",
  "red darken-4",

  "orange lighten-5",
  "orange lighten-4",
  "orange lighten-3",
  "orange lighten-2",
  "orange lighten-1",
  "orange darken-1",
  "orange darken-2",
  "orange darken-3",
  "orange darken-4",

  "yellow lighten-5",
  "yellow lighten-4",
  "yellow lighten-3",
  "yellow lighten-2",
  "yellow lighten-1",
  "yellow darken-1",
  "yellow darken-2",
  "yellow darken-3",
  "yellow darken-4",

  "green lighten-5",
  "green lighten-4",
  "green lighten-3",
  "green lighten-2",
  "green lighten-1",
  "green darken-1",
  "green darken-2",
  "green darken-3",
  "green darken-4",

  "teal lighten-5",
  "teal lighten-4",
  "teal lighten-3",
  "teal lighten-2",
  "teal lighten-1",
  "teal darken-1",
  "teal darken-2",
  "teal darken-3",
  "teal darken-4",

  "blue lighten-5",
  "blue lighten-4",
  "blue lighten-3",
  "blue lighten-2",
  "blue lighten-1",
  "blue darken-1",
  "blue darken-2",
  "blue darken-3",
  "blue darken-4",

  "indigo lighten-5",
  "indigo lighten-4",
  "indigo lighten-3",
  "indigo lighten-2",
  "indigo lighten-1",
  "indigo darken-1",
  "indigo darken-2",
  "indigo darken-3",
  "indigo darken-4",

  "purple lighten-5",
  "purple lighten-4",
  "purple lighten-3",
  "purple lighten-2",
  "purple lighten-1",
  "purple darken-1",
  "purple darken-2",
  "purple darken-3",
  "purple darken-4",

  "pink lighten-5",
  "pink lighten-4",
  "pink lighten-3",
  "pink lighten-2",
  "pink lighten-1",
  "pink darken-1",
  "pink darken-2",
  "pink darken-3",
  "pink darken-4",
];
