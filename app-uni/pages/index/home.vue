<template>
	<cl-page background-color="#fff">
		<view class="page-home">
			<cl-sticky>
				<cl-topbar :border="false" :show-back="false">
					<text
						class="title"
						:class="{
							show: scrollTop > 40,
						}"
					>
						{{ app.info.name }} 快速开发脚手架
					</text>
				</cl-topbar>
			</cl-sticky>

			<view class="logo">
				<text class="name">{{ app.info.name }}</text>
				<text class="version">v7.3.0</text>
			</view>

			<view class="container">
				<view class="group" v-for="(item, index) in list" :key="index">
					<text class="label">{{ item.label }}</text>

					<view class="list">
						<view
							class="item"
							v-for="(item2, index2) in item.children"
							:key="index2"
							@tap="toLink(item2.path)"
						>
							<text class="name">{{ item2.label }}</text>

							<cl-icon name="arrow-right" color="info"></cl-icon>
						</view>
					</view>
				</view>
			</view>
		</view>

		<tabbar />
	</cl-page>
</template>

<script lang="ts" setup>
import { useApp, useCool, module } from "/@/cool";
import { useUi } from "/$/cool-ui";
import Tabbar from "./components/tabbar.vue";
import { onPageScroll, onReady } from "@dcloudio/uni-app";
import { ref } from "vue";
import { isEmpty } from "lodash-es";

const { router, service } = useCool();
const ui = useUi();
const app = useApp();

const scrollTop = ref(0);

onPageScroll((e) => {
	scrollTop.value = e.scrollTop;
});

const list = ref([
	{
		label: "基础组件",
		value: "basic",
		children: [] as any[],
	},
	{
		label: "表单组件",
		value: "form",
		children: [],
	},
	{
		label: "视图组件",
		value: "view",
		children: [],
	},
	{
		label: "高级组件",
		value: "extend",
		children: [],
	},
]);

function toLink(path: string) {
	router.push({
		path,
		isGuard: false,
	});
}

onReady(() => {
	// cool-ui 示例
	router.routes.forEach((e) => {
		if (e.path.includes("pages/demo")) {
			const [, , key] = e.path.split("/");

			const item = list.value.find((e) => e.value == key);

			if (item) {
				item.children.push({
					label: e.style.navigationBarTitleText,
					path: "/" + e.path,
				});
			}
		}
	});

	// 插件示例
	const children = module.list.filter((e) => e.demo).map((e) => e.demo!);

	if (!isEmpty(children)) {
		list.value.unshift({
			label: "插件 / 模块",
			value: "plugin",
			children,
		});
	}
});
</script>

<style lang="scss" scoped>
@mixin title {
	background: linear-gradient(to right, #6b69f8, #a35df2, #d14bd8);
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
	font-weight: bold;
}

.page-home {
	.title {
		font-size: 32rpx;
		padding: 0 24rpx;
		opacity: 0;
		transition: opacity 0.5s ease-in-out;
		@include title();

		&.show {
			opacity: 1;
		}
	}

	.logo {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		padding: 20rpx 0 100rpx 0;
		font-weight: bold;

		.name {
			font-size: 80rpx;
			animation: showName 2.5s forwards;
			@include title();
		}

		.desc,
		.version {
			font-size: 28rpx;
			margin-top: 10rpx;
			animation: showV 2.5s forwards;
		}

		.desc {
			color: #444;
		}

		.version {
			background-color: $cl-color-primary;
			color: #fff;
			padding: 4rpx 10rpx;
			border-radius: 10rpx;
			margin-top: 20rpx;
		}
	}

	.dd {
		display: flex;
		padding: 50rpx;

		.a {
			flex: 1;
		}
	}

	@keyframes showName {
		from {
			letter-spacing: -40rpx;
			filter: blur(20rpx);
		}

		to {
			letter-spacing: 6rpx;
		}
	}

	@keyframes showV {
		from {
			letter-spacing: -10rpx;
			filter: blur(20rpx);
		}

		to {
			letter-spacing: 1rpx;
		}
	}

	.container {
		border-radius: 64rpx 64rpx 0 0;
		background-color: $cl-color-bg;
	}

	.group {
		padding: 30rpx;

		.label {
			display: block;
			margin-bottom: 20rpx;
			margin-left: 10rpx;
			font-size: 26rpx;
			color: #999;
		}

		.list {
			.item {
				display: flex;
				align-items: center;
				height: 80rpx;
				padding: 0 30rpx;
				margin-bottom: 25rpx;
				background-color: #fff;
				border-radius: 80rpx;
				box-shadow: 0 1rpx 8rpx #6666660f;

				.name {
					flex: 1;
					font-size: 28rpx;
					font-weight: bold;
				}
			}
		}
	}
}
</style>
