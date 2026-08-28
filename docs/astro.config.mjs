// @ts-check
import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';
import starlightLlmsTxt from 'starlight-llms-txt';

// https://astro.build/config
export default defineConfig({
	site: 'https://colors.rocketbase.io',
	integrations: [
		starlight({
			title: 'colors',
			description:
				'Java color library — named palettes (rocketbase, Tailwind, Open Color) plus helpers for hex, rgb, hsl and oklch.',
			logo: {
				light: './src/assets/logomark.svg',
				dark: './src/assets/logomark-dark.svg',
			},
			social: [
				{
					icon: 'github',
					label: 'GitHub',
					href: 'https://github.com/rocketbase-io/colors',
				},
			],
			editLink: {
				baseUrl: 'https://github.com/rocketbase-io/colors/edit/master/docs/',
			},
			tableOfContents: false,
			plugins: [
				starlightLlmsTxt({
					description:
						'Java color library with named palettes and conversions between hex, rgb, hsl, oklab and oklch. Maven: io.rocketbase.commons:colors',
				}),
			],
			customCss: ['./src/styles/custom.css'],
			sidebar: [
				{
					label: 'Start Here',
					items: ['getting-started'],
				},
				{
					label: 'Guides',
					items: ['color-spaces', 'contrast', 'nearest-and-random'],
				},
				{
					label: 'Palettes',
					items: ['palettes/color-palette', 'palettes/tailwind', 'palettes/open-color'],
				},
				{ label: 'Releases', link: '/releases/' },
			],
		}),
	],
});
